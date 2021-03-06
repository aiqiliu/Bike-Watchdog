package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

/**
 * Created by William on 4/22/2017
 */

class WatchdogListener implements LocationListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = "LocationListener";
    private GpsManager gpsManager;

    public WatchdogListener(GpsManager gpsManager) {
        this.gpsManager = gpsManager;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.v(TAG, "Location changed! " + location.toString());
        gpsManager.onLocChanged(location);
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
