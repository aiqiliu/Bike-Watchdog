package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Pairs
 * Created by William on 4/22/2017
 */

class PairBluetoothTask extends AsyncTask {
    private static final String TAG = "PairBluetoothTask";

    public PairBluetoothTask() {
        //TODO: update with parameters needed to run pairing (i.e. target mac address, context)
        Log.e(TAG, "Not yet implemented");
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e(TAG, "Not yet implemented");
        // TODO: check if already paired
        // TODO: pair if needed
        // TODO: store paired mac in file (coordinate with group)
        Log.e(TAG, "Not yet implemented");
        return null;
    }
}
