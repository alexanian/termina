package org.sfsi.termina.inputs;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ScrollView;

import com.bluelinelabs.conductor.RouterTransaction;

import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.sfsi.termina.options.OptionsController;
import org.sfsi.termina.R;
import org.sfsi.termina.TerminaNetwork;

import java.util.Calendar;

@EViewGroup(R.layout.input_view)
public class InputView extends ScrollView {
    /* package */ InputController mController;
    /* package */ ArrayAdapter<CharSequence> mAdapter;

    @ViewById(R.id.last_period_button)
    Button mLastPeriodButton;

    @ViewById(R.id.state_input)
    Button mStateButton;

    @ViewById(R.id.age_button)
    Button mAgeButton;

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
        final InputView inputView = InputView_.build(context);
        inputView.mController = controller;

        inputView.mAgeButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence ages[] = view.getContext().getResources().getStringArray(R.array.ages_array);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pick your age");
                builder.setItems(ages, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputView.mAgeButton.setText(ages[which]);
                        inputView.mController.mAge = ages[which].toString();
                    }
                });
                builder.show();
            }
        });

        inputView.mStateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(final View view) {
                final CharSequence states[] = view.getContext().getResources().getStringArray(R.array.input_states_array);

                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Pick your age");
                builder.setItems(states, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        inputView.mStateButton.setText(states[which]);
                        inputView.mController.mState = states[which].toString();
                    }
                });
                builder.show();
            }
        });

        return inputView;
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
                        mController.getRouter().pushController(RouterTransaction.with(new OptionsController()));
                    }
                });
            }
        };
        try {
            TerminaNetwork.getInstance().requestOptions(mController.mDaysSince, mController.mState, parseAge(mAgeButton.getText().toString()), callback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Click(R.id.last_period_button)
    protected void lastPeriodInputClicked() {
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                mController,
                mController.mCalendar.get(Calendar.YEAR),
                mController.mCalendar.get(Calendar.MONTH),
                mController.mCalendar.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.colorPrimary));
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

    private int parseAge(String ageText) {
        switch (ageText) {
            case "18 & over":
                return 18;
            case "17":
                return 17;
            case "16":
                return 16;
            default:
                return 15;
        }
    }
}
