package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.location.LocationManager;

/**
 * Created by William on 4/22/2017
 */

public class GpsManager {
    private static final String TAG = "GpsManager";
    private LocationManager mLocationManager;
    private WatchdogListener watchdogListener;
    private float threshold = Float.MAX_VALUE;

    /**
     * Initializes GpsManager to update only when threshold is passed
     * @param threshold of distance changed (in meters) before update received
     */
    public GpsManager(float threshold) {
        //TODO: initialize mLocationManager
        //TODO: initialize watchdogListener
        this.threshold = threshold;
    }

    /**
     * Starts the gps listener
     * @return true if success, false if failure
     */
    public boolean startListening() {
        //TODO: set WatchdogListener to run every time threshold is met
        // See https://developer.android.com/guide/topics/location/strategies.html
        return false;
    }

    /**
     * Stops the gps listener
     * @return true if success, false if failure
     */
    public boolean stopListening() {
        //TODO: unregister watchdogListener
        return false;
    }

    public void updateThreshold(float newThreshold) {
        //TODO: update this.threshold and reregister listener if needed
    }

}
