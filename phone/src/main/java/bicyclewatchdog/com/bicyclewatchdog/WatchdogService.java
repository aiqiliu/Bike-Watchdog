package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WatchdogService extends Service {
    public WatchdogService() {
    }

    //TODO: Add IPC to receive new instructions from MainActivity
    //TODO: Start/stop gps based off instructions from MainActivity
    //TODO: Add callback to initiate bluetooth

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
