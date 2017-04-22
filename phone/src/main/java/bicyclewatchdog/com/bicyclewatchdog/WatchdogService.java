package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

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
}
