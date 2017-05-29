package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import bicyclewatchdog.com.bicyclewatchdog.MyPreferences;
import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;

/**
 * Created by William on 4/22/2017
 */

public class GpsManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "GpsManager";
    private LocationManager mLocationManager;
    private WatchdogListener watchdogListener;
    private GoogleApiClient mGoogleApiClient;
    private float threshold = Float.MAX_VALUE;
    private boolean shouldResume = false;
    private Context context;
    private CustomBluetoothManager mBluetoothManager;
    private long interval = java.util.concurrent.TimeUnit.MINUTES.toMillis(1);

    /**
     * Initializes GpsManager to update only when threshold is passed
     *
     * @param btManager for use in onLocChanged()
     */
    public GpsManager(CustomBluetoothManager btManager, Context c) {
        context = c;
        mBluetoothManager = btManager;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        watchdogListener = new WatchdogListener(this);

        mGoogleApiClient = new GoogleApiClient.Builder(context)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

        mGoogleApiClient.connect();

    }

    /**
     * Starts the gps listener if not already listening
     *
     * @return true if success, false if failure
     */
    public boolean resumeGPS() {
        if (!mGoogleApiClient.isConnected()) {
            Log.v(TAG, "Queuing up to resume");
            shouldResume = true;
            mGoogleApiClient.connect();
            return true;
        }

        try {
            // Create the location request
            LocationRequest mLocationRequest = LocationRequest.create()
                    .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                    .setSmallestDisplacement(threshold)
                    .setInterval(interval);
            // Request location updates
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                    mLocationRequest, watchdogListener);
            return true;
        } catch (SecurityException e) {
            e.printStackTrace();
            return false;
        }

    }

    /**
     * Stops the gps listener
     *
     * @return true if success, false if failure
     */
    public boolean pauseGPS() {
        try {
            mLocationManager.removeUpdates(watchdogListener);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Updates the listener to listen for the proper threshold
     *
     * @param newThreshold to listen for
     */
    public void updateThreshold(float newThreshold) {
        if (newThreshold != threshold) {
            Log.v(TAG, "Setting new threshold");
            threshold = newThreshold;
            pauseGPS();
            resumeGPS();
        }
    }

    public float getThreshold() {return threshold;}

    /**
     * Called when threshold is met, searches for paired device.
     *
     * @param location of the device
     */
    protected void onLocChanged(Location location) {
        Log.e(TAG, "Telling BTmanager to search for device");
        SharedPreferences preferences = context.getSharedPreferences(MyPreferences.NAME, Context.MODE_PRIVATE);

        String lastLoc = preferences.getString(MyPreferences.KEY_LOCATION, "");
        String locationString = "Your bicycle has moved! Location: " +
                Double.toString(location.getLatitude()) + ", " + Double.toString(location.getLongitude());

        // Only update if the strings are not equal
        if (!lastLoc.equals(locationString)) {
            preferences.edit().putString(MyPreferences.KEY_LOCATION, locationString).apply();

            Toast.makeText(context, "GPS changed", Toast.LENGTH_SHORT).show();
            mBluetoothManager.findPairedDevice();
        } else {
            Log.v(TAG, "Location didn't actually change...");
        }
    }


    /* ************************** GOOGLE API CALLBACKS ******************************** */

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.v(TAG, "Successful connection to google api");
        if (shouldResume) {
            Log.v(TAG, "Calling resumeGPS...");
            resumeGPS();
            shouldResume = false;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.w(TAG, "Google API connection suspended");
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.e(TAG, "Failed to connect to google api");
    }
}
