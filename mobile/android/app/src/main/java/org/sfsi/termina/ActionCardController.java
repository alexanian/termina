package org.sfsi.termina;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;
import com.borax12.materialdaterangepicker.date.DatePickerDialog;
import org.sfsi.termina.customviews.ActionCardView;

import java.util.Calendar;

/**
 * Created by Smule on 3/4/17.
 */

public class ActionCardController extends Controller {
    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_action_cards, container, false);


        return view;
    }
}
