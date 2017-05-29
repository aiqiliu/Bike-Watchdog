package bicyclewatchdog.com.bicyclewatchdog;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.bluetooth_management.CustomBluetoothManager;
import bicyclewatchdog.com.bicyclewatchdog.gps_management.GpsManager;
import bicyclewatchdog.com.bicyclewatchdog.message_management.MessageManager;

public class WatchdogService extends Service {
    private static final String TAG = "WatchdogService";
    private final float DEFAULT_THRESHOLD = 10; // Default to 100 meter change

    // Activity reference for updating UI
    Callbacks activity;
    private final IBinder mBinder = new LocalBinder();

    // Managers
    private CustomBluetoothManager btManager;
    private GpsManager mGpsManager;
    private MessageManager messageManager;

    // Representation of the mac address we are paired to
    private String targetMac;

    public WatchdogService() {
        // Init managers
        messageManager = new MessageManager();
        btManager = new CustomBluetoothManager(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v(TAG, "Start command called");

        if (mGpsManager == null) {
            mGpsManager = new GpsManager(btManager, this.getApplication().getApplicationContext(),
                    messageManager);
            mGpsManager.resumeGPS();
        }
        bluetoothReciever.register();

        SharedPreferences preferences = getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE);
        String phoneNumber = preferences.getString(MyPreferences.KEY_PHONE, "");
        int type = preferences.getInt(MyPreferences.KEY_TYPE, MessageManager.TYPE_BICYCLE);
        float threshold = preferences.getFloat(MyPreferences.KEY_THRESHOLD, DEFAULT_THRESHOLD);
        String mac = preferences.getString(MyPreferences.KEY_MAC, "");

        messageManager.setPhoneNumber(phoneNumber);
        messageManager.setType(type);
        mGpsManager.updateThreshold(threshold);
        targetMac = mac;

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        Log.v(TAG, "Service destroyed");
        bluetoothReciever.unregister();
    }


    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        activity = null;
        return false;
    }


    public interface Callbacks {
        // Insert any methods to be run on the activity here
    }

    public class LocalBinder extends Binder {
        public WatchdogService getServiceInstance() {
            return WatchdogService.this;
        }
    }

    /* ********************* CALLBACK FOR BLUETOOTH DISCOVERY ************************** */

    BluetoothReceiver bluetoothReciever = new BluetoothReceiver();

    private class BluetoothReceiver extends BroadcastReceiver {
        protected boolean isregistered = false;

        public BluetoothReceiver() {
            super();
        }

        public void register() {
            if (!isregistered) {
                IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
                filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
                getApplicationContext().registerReceiver(this, filter);
                isregistered = true;
            }
        }

        public void unregister() {
            getApplicationContext().unregisterReceiver(this);
            isregistered = false;
        }

        private boolean isDeviceFound = false;

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Started searching, so we have not yet found the device
                isDeviceFound = false;

                // Discovery has found a device. Get the BluetoothDevice
                // object and its info from the Intent.
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                String deviceHardwareAddress = device.getAddress(); // MAC address

                Log.v(TAG, "FOUND DEVICE: " + deviceHardwareAddress);
                if (deviceHardwareAddress.equals(targetMac)) {
                    Log.e(TAG, "FOUND DEVICE!");
                    isDeviceFound = true;
                    btManager.stopSearch();
                } else {
                    Log.v(TAG, "Device does not match target " + targetMac);
                }
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                btManager.stopSearch();
                if (!isDeviceFound)
                    messageManager.sendMessage("Your bike is moving!", WatchdogService.this);
            }
        }
    }

    /* ********** PUBLIC METHODS FOR BINDING ACTIVITY TO CALL ********* */

    public void registerCallbacks(Callbacks callbacks) {
        activity = callbacks;
    }

    /**
     * Updates the threshold of the active gps manager
     *
     * @param threshold in meters
     */
    public void updateThreshold(float threshold) {
        Log.v(TAG, "Updating threshold to " + Float.toString(threshold));
        mGpsManager.updateThreshold(threshold);

        SharedPreferences preferences = getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE);
        preferences.edit().putFloat(MyPreferences.KEY_THRESHOLD, threshold).apply();
    }

    /**
     * Updates the type that the messenger uses to send messages
     *
     * @param type BICYCLE or PHONE
     */
    public void updateType(FunctionType type) {
        Log.v(TAG, "Updating type to " + type.toString());
        int intType;
        switch (type) {
            case PHONE:
                intType = MessageManager.TYPE_PHONE;
                break;
            case BICYCLE:
                intType = MessageManager.TYPE_BICYCLE;
                break;
            default:
                Log.e(TAG, "Unrecognized type sent to updateType");
                return;
        }

        messageManager.setType(intType);
        SharedPreferences preferences = getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE);
        preferences.edit().putInt(MyPreferences.KEY_TYPE, intType).apply();
    }

    public void sendTestMessage(String phoneNumber) {
        messageManager.setPhoneNumber(phoneNumber);
        messageManager.setTextMsg("Hello, World!");
        messageManager.sendMessage("Test message.", this);
    }

    public void updatePhone(String number) {
        Log.v(TAG, "Updating phone to " + number);
        messageManager.setPhoneNumber(number);

        SharedPreferences preferences = getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE);
        preferences.edit().putString(MyPreferences.KEY_PHONE, number).apply();
    }

    public void updateMac(String mac) {
        Log.v(TAG, "Updating mac address to " + mac);
        targetMac = mac;

        SharedPreferences preferences = getSharedPreferences(MyPreferences.NAME, MODE_PRIVATE);
        preferences.edit().putString(MyPreferences.KEY_MAC, mac).apply();
    }

    enum FunctionType {
        BICYCLE,
        PHONE,
        UNKNOWN
    }

}
