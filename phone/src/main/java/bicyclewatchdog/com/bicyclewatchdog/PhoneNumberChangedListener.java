package bicyclewatchdog.com.bicyclewatchdog;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Listens for change in the phone number and updates the service
 * Created by William on 4/30/2017.
 */

class PhoneNumberChangedListener implements View.OnFocusChangeListener {
    private static final String TAG = "NumberChangeListener";
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            Log.v(TAG, "Phone number changed to: " + ((EditText) v).getText());
        }
    }
}
