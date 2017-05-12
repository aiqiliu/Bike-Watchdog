package bicyclewatchdog.com.bicyclewatchdog.message_management;

import android.content.Context;
import android.util.Log;
import android.telephony.SmsManager;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.Manifest;
import android.widget.Toast;
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
    public void sendMessage(String content) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNumber, null, textmsg, null, null);
            Log.d("MSG", "msg sent");
        } catch (Exception ex) {
            Log.d("MSG", "msg failed to sent");
        }

    }

}


//public class MessageManager extends Context {
//    public static final int TYPE_BICYCLE = 0;
//    public static final int TYPE_PHONE = 1;
//
//    private static final String TAG = "MessageManager";
//    private String phoneNumber = "";
//    private int type = 0; // Default to bicycle
//    private String textmsg = "";
//    private static final int MY_PERMISSIONS_REQUEST_SEND_SMS = 0;
//
//    /**
//     * Sets the type of device
//     *
//     * @param type of device
//     */
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    /**
//     * Sets the destination number to number
//     *
//     * @param number of destination for text
//     */
//    public void setPhoneNumber(String number) {
//        phoneNumber = number;
//    }
//
//    /**
//     * Sets the content of the text message
//     *
//     * @param location of the gps coordinates
//     */
//    public void setTextMsg(String location) {
//        textmsg = "Bike location at: " + location;
//    }
//    /**
//     * Sends content to phoneNumber
//     * @param content of the message to send
//     *
//     *
//     */
//
//    public void sendSMS(String phoneNo, String msg) {
//        try {
//            SmsManager smsManager = SmsManager.getDefault();
//            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
//            Toast.makeText(getApplicationContext(), "Message Sent",
//                    Toast.LENGTH_LONG).show();
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(),ex.getMessage().toString(),
//                    Toast.LENGTH_LONG).show();
//            ex.printStackTrace();
//        }
//    }
//
//}
//    public void sendMessage(String content) {
//        //TODO: send the message to the stored phone number if type == BICYCLE
//        if (type == 0) {
//            // the client is the bike
//            textmsg = "Bike location at: ";
//            if (ContextCompat.checkSelfPermission(this,
//                    Manifest.permission.SEND_SMS)
//                    != PackageManager.PERMISSION_GRANTED) {
//                if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                        Manifest.permission.SEND_SMS)) {
//                } else {
//                    ActivityCompat.requestPermissions(this,
//                            new String[]{Manifest.permission.SEND_SMS},
//                            MY_PERMISSIONS_REQUEST_SEND_SMS);
//                }
//            }
//        }
//    }


//    public void onRequestPermissionsResult(int requestCode,String permissions[], int[] grantResults) {
//        switch (requestCode) {
//            case MY_PERMISSIONS_REQUEST_SEND_SMS: {
//                if (grantResults.length > 0
//                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                    SmsManager smsManager = SmsManager.getDefault();
//                    smsManager.sendTextMessage(phoneNumber, null, textmsg, null, null);
//                    Log.d("MSG", "msg sent");
//                } else {
//                    Log.d("MSG", "SMS faild, please try again.");
//                    return;
//                }
//            }
//        }
//
//
//    }
