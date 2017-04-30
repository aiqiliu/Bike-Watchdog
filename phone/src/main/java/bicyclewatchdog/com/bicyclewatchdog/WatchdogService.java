package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.gps_management.GpsManager;

public class WatchdogService extends Service {
    private static final String TAG = "WatchdogService";
    private final float DEFAULT_THRESHOLD = 100; // Default to 100 meter change
    private GpsManager mGpsManager;


    public WatchdogService() {
        // TODO: init mGpsManager with a threshold
    }

    //TODO: Add IPC to receive new instructions from MainActivity
    //TODO: Start/stop gps based off instructions from MainActivity
    //TODO: Update mGpsManager with new threshold based off IPC from MainActivity

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    /* ********** PUBLIC METHODS FOR BINDING ACTIVITY TO CALL ********* */

    /**
     * Updates the threshold of the active gps manager
     * @param threshold in meters
     */
    public void updateThreshold(float threshold) {
        Log.v(TAG, "Updating threshold to " + Float.toString(threshold));
        // TODO: call mGpsManager.updateThreshold(threshold);
    }

    /**
     * Updates the type that the messenger uses to send messages
     * @param type BICYCLE or PHONE
     */
    public void updateType(int type) {
        Log.v(TAG, "Updating type to " + Integer.toString(type));
        // TODO: define type enum
        // TODO: implement this
    }

    public void updatePhone(String number) {
        Log.v(TAG, "Updating phone to " + number);
        // TODO: implement updatePhone
    }


}
