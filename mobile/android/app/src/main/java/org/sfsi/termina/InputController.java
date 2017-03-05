package org.sfsi.termina;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Controller;

/**
 * Created by Smule on 3/4/17.
 */

public class InputController extends Controller {
    /* package */ InputView mInputView;

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
}
