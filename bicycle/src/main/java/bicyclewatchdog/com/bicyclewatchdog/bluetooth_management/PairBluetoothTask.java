package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Pairs
 * Created by William on 4/22/2017
 */

public class PairBluetoothTask extends AsyncTask {
    private static final String TAG = "PairBluetoothTask";

    public PairBluetoothTask() {
        //TODO: update with parameters needed to run pairing (i.e. target mac address, context)
    }

    @Override
    protected Object doInBackground(Object[] params) {
        Log.e(TAG, "Not yet implemented");
        // TODO: on successful pairing, permanently store MAC address in a file
        return null;
    }
}
