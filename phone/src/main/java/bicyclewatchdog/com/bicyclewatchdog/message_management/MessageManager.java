package bicyclewatchdog.com.bicyclewatchdog.message_management;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.util.Log;

import bicyclewatchdog.com.bicyclewatchdog.MyPreferences;

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
    private String textmsg = "";

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
     * Sets the content of the text message
     *
     * @param location of the gps coordinates
     */
    public void setTextMsg(String location) {
        textmsg = "Bike location at: " + location;
    }

    /**
     * Sends content to phoneNumber
     * @param content of the message to send
     */
    public void sendMessage(String content, Context context) {
        if (type == TYPE_PHONE) {
            Log.w(TAG, "Message request as type phone ignored");
        }

        if (textmsg == "") {
            SharedPreferences preferences =
                    context.getSharedPreferences(MyPreferences.NAME, Context.MODE_PRIVATE);
            String locationString = preferences.getString(MyPreferences.KEY_LOCATION, content);
            textmsg = locationString;
        }

        try {
            SmsManager smsManager = SmsManager.getDefault();
            Log.d("MSG", "msg sent: " + textmsg);
            smsManager.sendTextMessage(phoneNumber, null, textmsg, null, null);
        } catch (Exception ex) {
            Log.d("MSG", "msg failed to sent");
            ex.printStackTrace();
        }

    }

}
