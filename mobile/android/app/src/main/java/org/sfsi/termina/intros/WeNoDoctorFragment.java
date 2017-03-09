package org.sfsi.termina.intros;

import agency.tango.materialintroscreen.SlideFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import org.sfsi.termina.R;

/**
 * Created by Sean on 3/4/17.
 */

public class WeNoDoctorFragment extends SlideFragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.we_no_doctor_layout, container, false);
        return view;
    }

    @Override
    public int backgroundColor() {
        return R.color.colorPrimaryLighter;
    }

    @Override
    public int buttonsColor() {
        return R.color.colorPrimaryLight;
    }


}
