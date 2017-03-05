package org.sfsi.termina;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;

import com.bluelinelabs.conductor.Conductor;
import com.bluelinelabs.conductor.Router;
import com.bluelinelabs.conductor.RouterTransaction;

import org.sfsi.termina.inputs.InputController;

public class MasterActivity extends AppCompatActivity {
    private Router router;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_master);

        ViewGroup container = (ViewGroup)findViewById(R.id.controller_container);

        try {
            TerminaNetwork.getInstance().requestCopy();
        } catch (Exception e) {
            e.printStackTrace();
        }

        router = Conductor.attachRouter(this, container, savedInstanceState);
        if (!router.hasRootController()) {
            router.setRoot(RouterTransaction.with(new InputController()));
        }
    }

    @Override
    public void onBackPressed() {
        if (!router.handleBack()) {
            super.onBackPressed();
        }
    }
}
