package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import java.util.Timer;

/**
 * Created by William on 4/22/2017.
 */

public class GpsManager {

    private GpsPollingTask pollingTask;
    private Timer mTimer;

    /**
     * Initializes GpsManager to check coordinates every interval milliseconds
     * @param interval in milliseconds between GPS polling
     */
    public GpsManager(int interval) {
        //TODO: initialize mTimer and pollingTask and set to run every interval ms
        // See http://stackoverflow.com/questions/18353689/how-to-repeat-a-task-after-a-fixed-amount-of-time-in-android
    }

    /**
     * Registers callback to run when gps coordinates change more than the threshold
     * @param callback GpsCallback to be run
     */
    public void registerCallback(GpsCallback callback) {
        //TODO: register the callback with the task if callback not already registered
    }

}
