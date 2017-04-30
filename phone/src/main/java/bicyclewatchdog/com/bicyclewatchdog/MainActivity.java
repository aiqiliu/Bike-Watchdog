package bicyclewatchdog.com.bicyclewatchdog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioGroup;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

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
    }

}
