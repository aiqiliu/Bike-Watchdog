package bicyclewatchdog.com.bicyclewatchdog.gps_management;

import java.util.LinkedList;
import java.util.List;
import java.util.TimerTask;

/**
 * Created by William on 4/22/2017.
 */

public class GpsPollingTask extends TimerTask {
    private List<GpsCallback> callbacks = new LinkedList<>();


    @Override
    public void run() {

    }
}
