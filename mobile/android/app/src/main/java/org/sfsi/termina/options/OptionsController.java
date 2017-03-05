package org.sfsi.termina.options;

import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;

import org.sfsi.termina.R;
import org.sfsi.termina.TerminaNetwork;
import org.sfsi.termina.inputs.InputView;
import org.sfsi.termina.models.Option;

import java.util.ArrayList;

public class OptionsController extends Controller {
    public static final String TAG = OptionsController.class.getSimpleName();
    /* package */ OptionsView mOptionsView;

    private ArrayList<Option> mOptions;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        mOptionsView = mOptionsView.newInstance(getActivity(), this);

        mOptions = TerminaNetwork.getInstance().getOptions();

        for (Option option : mOptions) {
            Log.d(TAG, option.toString());
        }
        return mOptionsView;
    }
}
