package org.sfsi.termina;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import org.androidannotations.annotations.EViewGroup;

/**
 * Created by Smule on 3/4/17.
 */

@EViewGroup(R.layout.input_view)
public class InputView extends FrameLayout {
    /* package */ InputController mController;

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    static public InputView newInstance(final Context context, InputController controller) {
        InputView view = InputView_.build(context);
        view.mController = controller;
        return view;
    }
}
