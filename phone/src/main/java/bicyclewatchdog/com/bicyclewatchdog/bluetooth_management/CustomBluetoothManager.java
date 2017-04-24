package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.content.Context;
import android.util.Log;

/**
 * Created by William on 4/24/2017
 */

public class CustomBluetoothManager {
    private static final String TAG = "CustomBluetoothManager";

    public CustomBluetoothManager(Context applicationContext, String pairedMac) {
        //TODO: Implement constructor as needed
        // Contact person in charge of service/activity if you want to change the constructor
        // to include useful things (i.e. Context or whatnot)
        Log.e(TAG, "Not yet implemented");
    }

    public void findPairedDevice() {
        //TODO: implement this... I don't really have suggestions so you have a lot of freedom
        // Probably will have to run on background thread, so use AsyncTask FindPairedTask
        Log.e(TAG, "Not yet implemented");
    }

    //TODO: watch for lost connection (somehow). On connection lost, resume GPS
}
