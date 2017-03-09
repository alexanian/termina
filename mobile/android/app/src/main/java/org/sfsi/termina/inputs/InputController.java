package org.sfsi.termina.inputs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.WindowManager;
import com.bluelinelabs.conductor.Controller;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class InputController extends Controller implements DatePickerDialog.OnDateSetListener{
    /* package */ InputView mInputView;
    /* package */ Calendar mCalendar = Calendar.getInstance();
    /* package */ String mState;
    /* package */ String mDateStart;
    /* package */ String mAge;
    /* package */ int mDaysSince;
    private SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd/yyyy", Locale.US);

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        mInputView = InputView.newInstance(getActivity(), this);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return mInputView;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        int displayMonthOfYear = monthOfYear + 1;
        mDateStart = displayMonthOfYear + "/" + dayOfMonth + "/" + year;
        mInputView.mLastPeriodButton.setText(mDateStart);
        try {
            Date startDate = mFormat.parse(mDateStart);
            long duration = System.currentTimeMillis() - startDate.getTime();
            mDaysSince = (int) TimeUnit.MILLISECONDS.toDays(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onAttach(@NonNull final View view) {
        super.onAttach(view);

        mInputView.mLastPeriodButton.setText(mDateStart);
        mInputView.mStateButton.setText(mState);
        mInputView.mAgeButton.setText(mAge);
    }
}
