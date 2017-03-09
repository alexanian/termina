package org.sfsi.termina.options;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import org.androidannotations.annotations.EViewGroup;
import org.sfsi.termina.R;

/**
 * Created by Alex Korzec on 3/5/17.
 */

@EViewGroup(R.layout.options_view)
public class OptionsView extends LinearLayout {
    /* package */ OptionsController mController;

    public OptionsView(Context context) {
        super(context);
    }

    public OptionsView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public OptionsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static public OptionsView newInstance(final Context context, OptionsController controller) {
        OptionsView view = OptionsView_.build(context);
        view.mController = controller;
        return view;
    }

}
