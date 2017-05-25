package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by William on 4/24/2017
 */

public class CustomBluetoothManager {
    private static final String TAG = "CustomBluetoothManager";
    private static final String MAC = "";

    public CustomBluetoothManager(Context applicationContext, String pairedMac) {
        //TODO: Implement constructor as needed
        // Contact person in charge of service/activity if you want to change the constructor
        // to include useful things (i.e. Context or whatnot)
        //TODO: Make sure all this is right
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Log.e(TAG, "Device doesn't support Bluetooth");
        }
        if (!mBluetoothAdapter.isEnabled()) {
            Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            applicationContext.startActivity(enableBtIntent);
        }
        //Log.e(TAG, "Not yet implemented");
    }

    public void findPairedDevice() {
        //TODO: implement this... I don't really have suggestions so you have a lot of freedom
        // Probably will have to run on background thread, so use AsyncTask FindPairedTask
        Log.e(TAG, "Not yet implemented");

    }

    //TODO: watch for lost connection (somehow). On connection lost, resume GPS
}
