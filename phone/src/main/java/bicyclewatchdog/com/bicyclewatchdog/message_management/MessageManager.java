package bicyclewatchdog.com.bicyclewatchdog.message_management;

import android.util.Log;

/**
 * Class used to send SMS messages
 * Created by William on 4/24/2017.
 */

public class MessageManager {
    public static final int TYPE_BICYCLE = 0;
    public static final int TYPE_PHONE = 1;

    private static final String TAG = "MessageManager";
    private String phoneNumber = "";
    private int type = 0; // Default to bicycle

    /**
     * Sets the type of device
     * @param type of device
     */
    public void setType(int type) {
        this.type = type;
    }

    /**
     * Sets the destination number to number
     * @param number of destination for text
     */
    public void setPhoneNumber(String number) {
        phoneNumber = number;
    }

    /**
     * Sends content to phoneNumber
     * @param content of the message to send
     */
    public void sendMessage(String content) {
        //TODO: send the message to the stored phone number if type == BICYCLE
        Log.e(TAG, "Not yet implemented");
    }

}
