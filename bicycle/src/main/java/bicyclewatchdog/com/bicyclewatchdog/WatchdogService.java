package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class WatchdogService extends Service {
    public WatchdogService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
