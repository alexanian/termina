package org.sfsi.termina.intros;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import android.content.Intent;
import android.os.Bundle;
import org.sfsi.termina.MasterActivity;

/**
 * Created by Sean on 3/4/17.
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        addSlide(new FirstIntroFragment());

        addSlide(new WeNoDoctorFragment());
    }

    @Override
    public void onFinish() {
        super.onFinish();

        Intent masterIntent = new Intent(IntroActivity.this, MasterActivity.class);
        startActivity(masterIntent);
    }
}
