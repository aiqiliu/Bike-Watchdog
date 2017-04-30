package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;
import bicyclewatchdog.com.bicyclewatchdog.gps_management.GpsManager;
import bicyclewatchdog.com.bicyclewatchdog.message_management.MessageManager;

public class WatchdogService extends Service {
    private static final String TAG = "WatchdogService";
    private final float DEFAULT_THRESHOLD = 100; // Default to 100 meter change

    // Activity reference for updating UI
    Callbacks activity;
    private final IBinder mBinder = new LocalBinder();

    // Managers
    private CustomBluetoothManager btManager;
    private GpsManager mGpsManager;
    private MessageManager messageManager;


    public WatchdogService() {
        // Init managers
        btManager = new CustomBluetoothManager(getApplicationContext(), "asdf");
        mGpsManager = new GpsManager(btManager);
        messageManager = new MessageManager();

    }

    //TODO: Add IPC to receive new instructions from MainActivity
    //TODO: Start/stop gps based off instructions from MainActivity
    //TODO: Update mGpsManager with new threshold based off IPC from MainActivity

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        activity = null;
        return false;
    }


    public interface Callbacks {
        // Insert any methods to be run on the activity here
    }

    public class LocalBinder extends Binder {
        public WatchdogService getServiceInstance() {
            return WatchdogService.this;
        }
    }

    /* ********** PUBLIC METHODS FOR BINDING ACTIVITY TO CALL ********* */

    public void registerCallbacks(Callbacks callbacks) {
        activity = callbacks;
    }

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
