package org.sfsi.termina.customviews;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.sfsi.termina.R;

/**
 * Created by Sean on 3/4/17.
 */

@EViewGroup(R.layout.action_card_layout)
public class ActionCardView extends PercentRelativeLayout {
    @ViewById(R.id.tv_title)
    protected TextView mTitleTextView;

    @ViewById(R.id.tv_subtitle)
    protected TextView mSubtitleTextView;

    @ViewById(R.id.tv_body)
    protected TextView mBodyTextView;

    @ViewById(R.id.tv_learn_more)
    protected TextView mLearnMoreTextView;

    public ActionCardView(final Context context) {
        super(context);
    }

    public ActionCardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public class Builder {
        String mTitle;
        String mBody;
        String mExpDate;
        boolean mIsAvailable = true;
        boolean mDoesExpire = false;
        int mNumWeeksTilExpire = 0;

        public Builder setTitle(String title){
            mTitle = title;
            return this;
        }

        public Builder setBody(String body){
            mBody = body;
            return this;
        }

        public Builder setExpDate(String expDate){
            mExpDate = expDate;
            return this;
        }

        public Builder setIsAvailable(boolean isAvailable){
            mIsAvailable = isAvailable;
            return this;
        }

        public Builder setDoesExpire(boolean doesExpire){
            mDoesExpire = doesExpire;
            return this;
        }

        public Builder setNumWeeksTilExpire(int numWeeksTilExpire){
            mNumWeeksTilExpire = numWeeksTilExpire;
            return this;
        }

        public void build() {
            if (!TextUtils.isEmpty(mTitle)) {
                mTitleTextView.setText(mTitle);
            }

            if (!mIsAvailable && mNumWeeksTilExpire > 0) {
                mSubtitleTextView.setText(getResources().getString(R.string.action_card_subtitle_not_available_from, mNumWeeksTilExpire));
            }

            if (mDoesExpire && TextUtils.isEmpty(mExpDate)) {
                mSubtitleTextView.setText(getResources().getString(R.string.action_card_subtitle_available_until, mExpDate));
            }

            if (!TextUtils.isEmpty(mBody)) {
                mBodyTextView.setText(mBody);
            }

            if (mBodyTextView.getLineCount() > 4) {
                mLearnMoreTextView.setVisibility(View.VISIBLE);
            }
        }
    }

}
