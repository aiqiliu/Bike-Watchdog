package bicyclewatchdog.com.bicyclewatchdog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    //TODO: Add IPC to communicate with service
    //TODO: Add buttons to pair to bike
    //TODO: Add option to turn off gps polling

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}
