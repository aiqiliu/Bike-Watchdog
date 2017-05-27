package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;

/**
 * Created by William on 4/22/2017
 */

public class WatchdogListener implements LocationListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = "LocationListener";
    private CustomBluetoothManager mBtManager;

    public WatchdogListener(CustomBluetoothManager btManager) {
        mBtManager = btManager;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(TAG, "Location changed! " + location.toString());

        // Call bluetooth manager -- check for paired device.
//        mBtManager.findPairedDevice();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //TODO: implement if needed
        Log.e(TAG, "Not yet implemented");
    }

    @Override
    public void onProviderEnabled(String provider) {
        //TODO: implement if needed
        Log.e(TAG, "Not yet implemented");
    }

    @Override
    public void onProviderDisabled(String provider) {
        //TODO: implement if needed
        Log.e(TAG, "Not yet implemented");
    }
}
