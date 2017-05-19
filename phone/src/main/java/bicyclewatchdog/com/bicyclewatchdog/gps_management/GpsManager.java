package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;

/**
 * Created by William on 4/22/2017
 */

public class GpsManager {
    private static final String TAG = "GpsManager";
    private LocationManager mLocationManager;
    private WatchdogListener watchdogListener;
    private float threshold = Float.MAX_VALUE;
    private Context context;
    private CustomBluetoothManager mBluetoothManager;

    /**
     * Initializes GpsManager to update only when threshold is passed
     * @param btManager for use in onLocChanged()
     */
    public GpsManager(CustomBluetoothManager btManager, Context c) {
        context = c;
        mBluetoothManager = btManager;
        mLocationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        watchdogListener = new WatchdogListener();

    }

    /**
     * Starts the gps listener if not already listening
     * @return true if success, false if failure
     */
    public boolean resumeGPS() {
        //TODO: set WatchdogListener to run every time threshold is met
        // See https://developer.android.com/guide/topics/location/strategies.html

        Log.v(TAG, "Checking permissions");
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED)
//            Log.v(TAG, "Fine location works");
//        else
//            Log.v(TAG, "Fine location doesnt work");
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED)
//            Log.v(TAG, "Coarse location works");
//        else
//            Log.v(TAG, "Coarse location doesnt work");
//
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
//                != PackageManager.PERMISSION_GRANTED &&
//                ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
//                        != PackageManager.PERMISSION_GRANTED) {
        try {
            mLocationManager.requestLocationUpdates("gps", 5000, 0, watchdogListener);
            Log.v(TAG, "Successfully requested location");
            return true;
        } catch (SecurityException e) {
            Log.e(TAG, "Permissions required not received");
            e.printStackTrace();
            return false;
        }
//        }

//        Log.e(TAG, "Failed to resume GPS");
//        return false;
    }

    /**
     * Stops the gps listener
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
     * @param newThreshold to listen for
     */
    public void updateThreshold(float newThreshold) {
        //TODO: update this.threshold and reregister listener if needed
        Log.e(TAG, "Not yet implemented");
    }

    /**
     * Called when threshold is met, searches for paired device.
     * @param location of the device
     */
    protected void onLocChanged(Location location) {
        //TODO: search for paired device
        // Feel free to move this into the WatchdogListener or make into
        // a runnable passed into WatchdogListener as needed
        Log.e(TAG, "Not yet implemented");
    }
}
