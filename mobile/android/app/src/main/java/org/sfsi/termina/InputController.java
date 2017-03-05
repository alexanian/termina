package org.sfsi.termina;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;

import java.util.Calendar;

/**
 * Created by Smule on 3/4/17.
 */

public class InputController extends Controller implements DatePickerDialog.OnDateSetListener{
    /* package */ InputView mInputView;
    /* package */ Calendar mCalendar = Calendar.getInstance();

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        mInputView = InputView.newInstance(getActivity(), this);
        try {
            TerminaNetwork.getInstance().run();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mInputView;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth, int yearEnd, int monthOfYearEnd, int dayOfMonthEnd) {
        mInputView.mLastPeriodEditText.setText(monthOfYear + "/" + dayOfMonth + "/" + year + " - " + monthOfYearEnd + "/" + dayOfMonthEnd + "/" +yearEnd);
    }
}
