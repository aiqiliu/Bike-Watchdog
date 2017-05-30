package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.Manifest;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;

import bicyclewatchdog.com.bicyclewatchdog.MyPreferences;
import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;
import bicyclewatchdog.com.bicyclewatchdog.message_management.MessageManager;

/**
 * Created by William on 4/22/2017
 */

public class GpsManager implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private static final String TAG = "GpsManager";
    private LocationManager mLocationManager;
    private WatchdogListener watchdogListener;
    private MessageManager messageManager;
    private float threshold = Float.MAX_VALUE;
    private boolean shouldResume = false;
    private Context context;
    private CustomBluetoothManager mBluetoothManager;
    private long interval = java.util.concurrent.TimeUnit.MINUTES.toMillis(2);

    private LocationManager locationManager;

    /**
     * Initializes GpsManager to update only when threshold is passed
     *
     * @param btManager for use in onLocChanged()
     */
    public GpsManager(CustomBluetoothManager btManager, Context c, MessageManager messageManager) {
        context = c;
        mBluetoothManager = btManager;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        this.messageManager = messageManager;

        watchdogListener = new WatchdogListener(this);

        locationManager = (LocationManager) c.getSystemService(Context.LOCATION_SERVICE);

        Criteria criteria = new Criteria();
        try {
            criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        } catch (IllegalArgumentException e) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "Permissions not granted for GPS");
        } else {
            locationManager.requestLocationUpdates(provider, interval, threshold, watchdogListener);
        }
    }

    /**
     * Starts the gps listener if not already listening
     *
     * @return true if success, false if failure
     */
    public boolean resumeGPS() {
        Criteria criteria = new Criteria();
        try {
            criteria.setAccuracy(Criteria.ACCURACY_HIGH);
        } catch (IllegalArgumentException e) {
            criteria.setAccuracy(Criteria.ACCURACY_COARSE);
        }

        String provider = locationManager.getBestProvider(criteria, true);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.e(TAG, "Permissions not granted for GPS");
            return false;
        } else {
            locationManager.requestLocationUpdates(provider, interval, threshold, watchdogListener);
            return true;
        }
    }

    /**
     * Stops the gps listener
     *
     * @return true if success, false if failure
     */
    public boolean pauseGPS() {
        locationManager.removeUpdates(watchdogListener);
        return true;
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

        Log.v(TAG, "Previous: " + lastLoc);
        Log.v(TAG, "Current: " + locationString);


        // Only update if the strings are not equal
        if (!lastLoc.equals(locationString)) {
            preferences.edit().putString(MyPreferences.KEY_LOCATION, locationString).commit();

            messageManager.setTextMsg(locationString);

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
