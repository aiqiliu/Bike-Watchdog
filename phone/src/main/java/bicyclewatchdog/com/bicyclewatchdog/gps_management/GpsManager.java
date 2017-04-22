package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.location.LocationManager;

/**
 * Created by William on 4/22/2017.
 */

public class GpsManager {
    LocationManager mLocationManager;

    /**
     * Initializes GpsManager to check coordinates every interval milliseconds
     * @param threshold of distance changed (in meters) before update received
     */
    public GpsManager(float threshold) {
        //TODO: initialize mLocationManager
        // Todo: set WatchdogListener to run every time threshold is met
        // See https://developer.android.com/guide/topics/location/strategies.html
    }

}
