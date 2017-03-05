package org.sfsi.termina;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.sfsi.termina.InputView_;

import java.util.Calendar;

/**
 * Created by Smule on 3/4/17.
 */

@EViewGroup(R.layout.input_view)
public class InputView extends ScrollView {
    /* package */ InputController mController;

    @ViewById(R.id.last_period_edit_text)
    EditText mLastPeriodEditText;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static public InputView newInstance(final Context context, InputController controller) {
        InputView view = InputView_.build(context);
        view.mController = controller;
        return view;
    }

    @Click(R.id.last_period_edit_text)
    protected void lastPeriodInputClicked() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                mController,
                mController.mCalendar.get(Calendar.YEAR),
                mController.mCalendar.get(Calendar.MONTH),
                mController.mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.show(getActivity().getFragmentManager(), "LastPeriodInputDatePickerDialog");
    }

    private Activity getActivity() {
        Context context = getContext();
        while (context instanceof ContextWrapper) {
            if (context instanceof Activity) {
                return (Activity)context;
            }
            context = ((ContextWrapper)context).getBaseContext();
        }
        return null;
    }
}
