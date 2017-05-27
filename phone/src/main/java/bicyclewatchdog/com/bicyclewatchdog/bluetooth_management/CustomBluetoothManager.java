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
    private BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();

    public CustomBluetoothManager(Context applicationContext) {
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
    }

    public void findPairedDevice() {
        BluetoothAdapter.getDefaultAdapter().startDiscovery();
    }

    public void stopSearch() {
        adapter.cancelDiscovery();
    }

    public void startSearch() {
        adapter.startDiscovery();
    }
}
