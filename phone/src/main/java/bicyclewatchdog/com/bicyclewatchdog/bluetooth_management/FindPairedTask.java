package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.gps_management.GpsManager;
import bicyclewatchdog.com.bicyclewatchdog.message_management.MessageManager;

/**
 * Searches for the device if paired
 * Created by William on 4/22/2017.
 */

class FindPairedTask extends AsyncTask {
    private static final String TAG = "FindPairedTask";

    private String targetMac;
    private Runnable onSuccess;
    private Runnable onFailure;
    private BluetoothAdapter mBluetoothAdapter;
    private GpsManager gps;
    private MessageManager msg;
    private Context context;

    public FindPairedTask(String targetMAC, Runnable onSuccess, Runnable onFailure,
                          BluetoothAdapter BTA, GpsManager gpsman, Context context) {
        targetMac = targetMAC;
        mBluetoothAdapter = BTA;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        gps = gpsman;
        this.context = context;
        Log.e(TAG, "Not yet implemented");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        // TODO: try to connect to device with mac targetMac

        // Create a BroadcastReceiver for ACTION_FOUND.
        final BroadcastReceiver mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Discovery has found a device. Get the BluetoothDevice
                    // object and its info from the Intent.
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    String deviceHardwareAddress = device.getAddress(); // MAC address
                    if (deviceHardwareAddress.equals(targetMac)){
                        mBluetoothAdapter.cancelDiscovery();
                        //TODO: Pause GPS
                        gps.pauseGPS();
                    }
                }
                else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)){
                    mBluetoothAdapter.cancelDiscovery();
                    //TODO: Go into a send-message loop until phone back in range
                    
                }
            }
        };

        // Register for broadcasts when a device is discovered.
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        context.getApplicationContext().registerReceiver(mReceiver, filter);

        mBluetoothAdapter.startDiscovery();
        return null;
    }
}