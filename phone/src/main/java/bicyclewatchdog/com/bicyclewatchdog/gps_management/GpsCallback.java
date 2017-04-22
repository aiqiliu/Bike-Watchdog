package bicyclewatchdog.com.bicyclewatchdog.gps_management;

/**
 * Created by William on 4/22/2017.
 */

public abstract class GpsCallback {
    boolean isRegistered = false;
    private float threshold = Float.MAX_VALUE;

    public GpsCallback(float threshold) {
        this.threshold = threshold;
    }

    public boolean isPastThreshold(float t) { return t > threshold; }

    public abstract void run();

}
