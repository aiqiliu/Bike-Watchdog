package bicyclewatchdog.com.bicyclewatchdog.gps_management;

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
    private CustomBluetoothManager mBluetoothManager;

    /**
     * Initializes GpsManager to update only when threshold is passed
     * @param btManager for use in onLocChanged()
     */
    public GpsManager(CustomBluetoothManager btManager) {
        mBluetoothManager = btManager;
        //TODO: initialize mLocationManager
        //TODO: initialize watchdogListener
        Log.e(TAG, "Not yet implemented");
    }

    /**
     * Starts the gps listener if not already listening
     * @return true if success, false if failure
     */
    public boolean resumeGPS() {
        //TODO: set WatchdogListener to run every time threshold is met
        // See https://developer.android.com/guide/topics/location/strategies.html
        Log.e(TAG, "Not yet implemented");
        return false;
    }

    /**
     * Stops the gps listener
     * @return true if success, false if failure
     */
    public boolean pauseGPS() {
        //TODO: unregister watchdogListener
        Log.e(TAG, "Not yet implemented");
        return false;
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
