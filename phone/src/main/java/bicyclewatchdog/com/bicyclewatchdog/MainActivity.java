package bicyclewatchdog.com.bicyclewatchdog;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WatchdogService.Callbacks {
    private static final String TAG = "MainActivity";

    // Reference to the service
    WatchdogService mService;

    //TODO: Add IPC to communicate with service
    //TODO: Add option to turn off gps polling
    //TODO: Unfocus on edittext when finish button pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set the change listener for the radio group
        ((RadioGroup) this.findViewById(R.id.radioGroupType))
                .setOnCheckedChangeListener(typeChangedListener);

        // Set the change listener for the threshold
        this.findViewById(R.id.editTextThreshold)
                .setOnFocusChangeListener(thresholdChangedListener);

        // Set the change listener for phone number
        this.findViewById(R.id.editTextPhone)
                .setOnFocusChangeListener(phoneNumberChangedListener);

        Intent serviceIntent = new Intent(this, WatchdogService.class);
        bindService(serviceIntent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mServiceConnection);
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


    /* ***************** UI LISTENERS ********************** */
    /**
     * Listener to handle changes of the focus on editTextPhoneNumber
     */
    private View.OnFocusChangeListener phoneNumberChangedListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                String phoneNumber = ((EditText) v).getText().toString();
                mService.updatePhone(phoneNumber);
            }
        }
    };

    /**
     * Listener to handle changes of the focus on editTextThreshold
     */
    private View.OnFocusChangeListener thresholdChangedListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (!hasFocus) {
                // Focus lost on the threshold EditText. Update service
                int threshold = Integer.parseInt(((EditText) v).getText().toString());
                mService.updateThreshold(threshold);
            }
        }
    };

    /**
     * Listener to handle changes of the type of app function
     */
    private RadioGroup.OnCheckedChangeListener typeChangedListener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
            WatchdogService.FunctionType type;

            if (group.getCheckedRadioButtonId() == R.id.radioButtonBike) {
                type = WatchdogService.FunctionType.BICYCLE;
            } else if (group.getCheckedRadioButtonId() == R.id.radioButtonPhone) {
                type = WatchdogService.FunctionType.PHONE;
            } else {
                Log.e(TAG, "Unknown function type selected!");
                type = WatchdogService.FunctionType.UNKNOWN;
            }

            mService.updateType(type);
        }
    };

    /**
     * Handles the pairing button clicked
     */
    public void onPairClicked(View v) {
        Log.v(TAG, "Pair clicked.");
    }

    /**
     * Handles the send message button clicked
     */
    public void onSendMessageClicked(View v) {
        String phoneNumber = ((EditText) findViewById(R.id.editTextPhone)).getText().toString();

        Toast.makeText(this, "Sending message to " + phoneNumber, Toast.LENGTH_SHORT).show();

        mService.sendTestMessage(phoneNumber);
    }
}
