package bicyclewatchdog.com.bicyclewatchdog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity implements WatchdogService.Callbacks{
    private static final String TAG = "MainActivity";

    // Reference to the service
    WatchdogService mService;

    //TODO: Add IPC to communicate with service
    //TODO: Add buttons to pair to bike
    //TODO: Add option to turn off gps polling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the change listener for the radio group
        ((RadioGroup) this.findViewById(R.id.radioGroupType))
                .setOnCheckedChangeListener(new TypeChangedListener());

        // Set the change listener for the threshold
        this.findViewById(R.id.editTextThreshold)
                .setOnFocusChangeListener(new ThresholdChangedListener());

        // Set the change listener for phone number
        this.findViewById(R.id.editTextPhone)
                .setOnFocusChangeListener(new PhoneNumberChangedListener());

        Intent serviceIntent = new Intent(this, WatchdogService.class);
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
    }


    public void onPairClicked(View v) {
        Log.v(TAG, "Pair clicked.");

    }


    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.v(TAG, "Connected!");
            mService = ((WatchdogService.LocalBinder) service).getServiceInstance();
            mService.registerCallbacks(MainActivity.this);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.v(TAG, "Service disconnected.");
            mService = null;
        }
    };
}
