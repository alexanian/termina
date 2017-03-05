package org.sfsi.termina;

import agency.tango.materialintroscreen.MaterialIntroActivity;
import agency.tango.materialintroscreen.MessageButtonBehaviour;
import agency.tango.materialintroscreen.SlideFragmentBuilder;
import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Sean on 3/4/17.
 */

public class IntroActivity extends MaterialIntroActivity {
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        
        addSlide(new SlideFragmentBuilder()
                        .backgroundColor(R.color.colorPrimary)
                        .buttonsColor(R.color.colorAccent)
                        .neededPermissions(new String[]{ Manifest.permission.INTERNET })
                        .image(agency.tango.materialintroscreen.R.drawable.ic_next)
                        .title("title 3")
                        .description("Description 3")
                        .build(),
                new MessageButtonBehaviour(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        showMessage("We provide solutions to make you love your work");
                    }
                }, "Work with love")
        );

        addSlide(new SlideFragmentBuilder()
                .backgroundColor(R.color.colorPrimary)
                .buttonsColor(R.color.colorAccent)
                .title("Want more?")
                .description("Go on")
                .build());
    }

    @Override
    public void onFinish() {
        super.onFinish();

        Intent masterIntent = new Intent(IntroActivity.this, MasterActivity.class);
        startActivity(masterIntent);
    }
}
