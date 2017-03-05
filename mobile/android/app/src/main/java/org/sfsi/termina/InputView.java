package org.sfsi.termina;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.Spinner;

import com.bluelinelabs.conductor.RouterTransaction;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.Calendar;

@EViewGroup(R.layout.input_view)
public class InputView extends ScrollView implements AdapterView.OnItemSelectedListener {
    /* package */ InputController mController;
    /* package */ ArrayAdapter<CharSequence> mAdapter;

    @ViewById(R.id.last_period_edit_text)
    EditText mLastPeriodEditText;

    @ViewById(R.id.age_edit_text)
    EditText mAgeEditText;

    @ViewById(R.id.fab_next)
    FloatingActionButton mButtonNext;

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

        Spinner spinner = (Spinner) view.findViewById(R.id.state_input);
        view.mAdapter = ArrayAdapter.createFromResource(context,
                R.array.input_states_array, android.R.layout.simple_spinner_item);
        view.mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(view.mAdapter);

        return view;
    }

    @Click(R.id.fab_next)
    protected void nextButtonClicked() {
        Runnable callback = new Runnable() {
            @Override
            public void run() {
                // TODO: Check if attached
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mController.getRouter().pushController(RouterTransaction.with(new ActionCardController()));
                    }
                });
            }
        };
        try {
            TerminaNetwork.getInstance().requestOptions(mController.mDaysSince, mController.mState, Integer.parseInt(mAgeEditText.getText().toString()), callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        mController.mState = mAdapter.getItem(i).toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
        // TODO: Error message
    }
}
