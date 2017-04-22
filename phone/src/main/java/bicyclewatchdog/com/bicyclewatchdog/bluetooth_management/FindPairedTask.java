package bicyclewatchdog.com.bicyclewatchdog.bluetooth_management;

import android.os.AsyncTask;

/**
 * Searches for the device if paired
 * Created by William on 4/22/2017.
 */

public class FindPairedTask extends AsyncTask {
    private static final String TAG = "FindPairedTask";

    private String targetMac;
    private Runnable onSuccess;
    private Runnable onFailure;

    public FindPairedTask(String targetMAC, Runnable onSuccess, Runnable onFailure) {
        targetMac = targetMAC;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    protected Object doInBackground(Object[] params) {
        // TODO: try to connect to device with mac targetMac
        return null;
    }
}
