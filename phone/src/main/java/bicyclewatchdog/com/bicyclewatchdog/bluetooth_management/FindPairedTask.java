package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

/**
 * Searches for the device if paired
 * Created by William on 4/22/2017.
 */

class FindPairedTask extends AsyncTask {
    private static final String TAG = "FindPairedTask";

    private String targetMac;
    private Runnable onSuccess;
    private Runnable onFailure;

    public FindPairedTask(String targetMAC, Runnable onSuccess, Runnable onFailure) {
        targetMac = targetMAC;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
        Log.e(TAG, "Not yet implemented");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        // TODO: try to connect to device with mac targetMac

        // TODO: on success, pause gps
        // TODO: on failure, send message
        Log.e(TAG, "Not yet implemented");
        return null;
    }
}
