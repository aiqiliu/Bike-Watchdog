package bicyclewatchdog.com.bicyclewatchdog;

import android.support.annotation.IdRes;
import android.util.Log;
import android.widget.RadioGroup;

/**
 * A listener for use of the main activity to detect changes in the type radio group.
 * Created by William on 4/29/2017.
 */

class TypeChangedListener implements RadioGroup.OnCheckedChangeListener {
    private static final String TAG = "TypeChangedListener";
    @Override
    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
        // TODO: Implement this.
        Log.v(TAG, "Type changed to: " + group.getCheckedRadioButtonId());

    }
}
