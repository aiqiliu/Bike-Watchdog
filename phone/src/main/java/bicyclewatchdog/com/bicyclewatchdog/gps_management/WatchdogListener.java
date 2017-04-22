package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

/**
 * Created by William on 4/22/2017
 */

public class WatchdogListener implements LocationListener {
    private static final String TAG = "LocationListener";

    @Override
    public void onLocationChanged(Location location) {
        // Todo: implement to begin bluetooth operiations
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {}

    @Override
    public void onProviderEnabled(String provider) {}

    @Override
    public void onProviderDisabled(String provider) {}
}
