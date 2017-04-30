package bicyclewatchdog.com.bicyclewatchdog;

import android.util.Log;
import android.view.View;
import android.widget.EditText;

/**
 * Class that watches for loss of focus in the threshold EditText
 * Updates the service appropriately when the focus is lost
 * Created by William on 4/30/2017.
 */

class ThresholdChangedListener implements View.OnFocusChangeListener {
    private static final String TAG = "ThresholdChanged";


    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            // Focus lost on the threshold EditText. Update service
            // TODO: Update constructor to get the context from view and talk to service
            Log.v(TAG, "Lost focus on " + v.getId());
            Log.v(TAG, "Threshold changed to " + ((EditText) v).getText());
        }
    }
}
