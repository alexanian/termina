package org.sfsi.termina;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;

import org.sfsi.termina.models.Option;

import java.util.ArrayList;

public class ActionCardController extends Controller {
    public static final String TAG = ActionCardController.class.getSimpleName();

    private ArrayList<Option> mOptions;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_action_cards, container, false);

        mOptions = TerminaNetwork.getInstance().getOptions();

        for (Option option : mOptions) {
            Log.d(TAG, option.toString());
        }
        return view;
    }
}
