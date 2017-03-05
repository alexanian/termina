package org.sfsi.termina.options;

import android.support.annotation.NonNull;
import android.support.percent.PercentRelativeLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.bluelinelabs.conductor.Controller;

import org.sfsi.termina.R;
import org.sfsi.termina.TerminaNetwork;
import org.sfsi.termina.customviews.ActionCardView;
import org.sfsi.termina.models.Option;
import org.sfsi.termina.models.OptionDetailsResponse;

import java.util.ArrayList;

public class OptionsController extends Controller {
    public static final String TAG = OptionsController.class.getSimpleName();
    /* package */ OptionsView mOptionsView;

    private ArrayList<Option> mOptions;

    private boolean shouldMoveFirstViewToBottom;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        mOptionsView = OptionsView.newInstance(getActivity(), this);

        mOptions = TerminaNetwork.getInstance().getOptions();

        for (Option option : mOptions) {
            Log.d(TAG, option.toString());
            OptionDetailsResponse optionDetailsResponse = TerminaNetwork.getInstance().getOptionDetailsResponse();

            ActionCardView.Builder builder = new ActionCardView.Builder(getActivity())
                    .setActionType(option.type, optionDetailsResponse)
                    .setIsAvailable(option.available);

            if (TerminaNetwork.getInstance().mDays > 84 && option.type.equals("medication")) {
                builder.setNumWeeksSinceExpired(12);
                shouldMoveFirstViewToBottom = true;
            }

            if (TerminaNetwork.getInstance().getAgeWarning() != null) {
                builder.setAgeWarningApplies(true, TerminaNetwork.getInstance().getAgeWarning().display_text);
            }

            ActionCardView view = builder.build();
            view.setLayoutParams(new PercentRelativeLayout.LayoutParams(PercentRelativeLayout.LayoutParams.WRAP_CONTENT, PercentRelativeLayout.LayoutParams.WRAP_CONTENT));
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).topMargin = (int) getResources().getDimension(R.dimen.margin_8);
            ((ViewGroup.MarginLayoutParams) view.getLayoutParams()).bottomMargin = (int) getResources().getDimension(R.dimen.margin_8);

            ((ViewGroup) ((ViewGroup) mOptionsView.getChildAt(0)).getChildAt(0)).addView(view); //lol hackathon
        }

        if (shouldMoveFirstViewToBottom) {
            View viewToMove = ((ViewGroup) ((ViewGroup) mOptionsView.getChildAt(0)).getChildAt(0)).getChildAt(0);
            ((ViewGroup) ((ViewGroup) mOptionsView.getChildAt(0)).getChildAt(0)).removeView(viewToMove);
            ((ViewGroup) ((ViewGroup) mOptionsView.getChildAt(0)).getChildAt(0)).addView(viewToMove);
        }

        return mOptionsView;
    }
}
