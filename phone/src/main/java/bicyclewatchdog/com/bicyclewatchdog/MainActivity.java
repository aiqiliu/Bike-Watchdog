package bicyclewatchdog.com.bicyclewatchdog;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.IdRes;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements WatchdogService.Callbacks {
    private static final String TAG = "MainActivity";
    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
    private static final int MY_PERMISSIONS_REQUEST_GPS = 1;

    // Reference to the service
    WatchdogService mService;

    //TODO: Add IPC to communicate with service
    //TODO: Add option to turn off gps polling
    //TODO: Unfocus on edittext when finish button pressed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.SEND_SMS, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET,
                        Manifest.permission.BLUETOOTH, Manifest.permission.BLUETOOTH_ADMIN},
                MY_PERMISSIONS_REQUEST_SEND_SMS);


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
        startService(serviceIntent);
        bindService(serviceIntent, mServiceConnection, Context.BIND_IMPORTANT);
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

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                }
                break;
            }
            case MY_PERMISSIONS_REQUEST_GPS:
                Log.v(TAG, "GPS permission granted");
                break;
        }
    }

}
