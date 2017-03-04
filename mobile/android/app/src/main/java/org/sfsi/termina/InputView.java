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

    public InputView(Context context) {
        super(context);
    }

    public InputView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public InputView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
