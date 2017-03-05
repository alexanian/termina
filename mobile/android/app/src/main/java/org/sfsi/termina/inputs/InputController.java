package org.sfsi.termina.inputs;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class InputController extends Controller implements DatePickerDialog.OnDateSetListener{
    /* package */ InputView mInputView;
    /* package */ Calendar mCalendar = Calendar.getInstance();
    /* package */ String mState;
    /* package */ int mDaysSince;
    private SimpleDateFormat mFormat = new SimpleDateFormat("MM/dd/yyyy");

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        mInputView = InputView.newInstance(getActivity(), this);
        return mInputView;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        String rangeStart = monthOfYear + "/" + dayOfMonth + "/" + year;
        String rangeEnd = monthOfYearEnd + "/" + dayOfMonthEnd + "/" + yearEnd;
        mInputView.mLastPeriodEditText.setText(rangeStart + " - " + rangeEnd);
        try {
            Date rangeStartDate = mFormat.parse(rangeStart);
            long duration = System.currentTimeMillis() - rangeStartDate.getTime();
            mDaysSince = (int) TimeUnit.MILLISECONDS.toDays(duration);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }
}
