package org.sfsi.termina.customviews;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.TextView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.sfsi.termina.R;

/**
 * Created by Sean on 3/4/17.
 */

@EViewGroup(R.layout.action_card_layout)
public class ActionCardView extends PercentRelativeLayout {
    public enum ActionType { MEDICATION, SURGICAL, CONTINUE }

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
        ActionType mActionType;
        String mTitle;
        String mBody;
        String mExpDate;
        boolean mIsAvailable = true;
        boolean mDoesExpire = false;
        int mNumWeeksTilExpire = 0;

        public Builder setTitleStyle(ActionType type) {
            mActionType = type;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setBody(String body) {
            mBody = body;
            return this;
        }

        public Builder setExpDate(String expDate) {
            mExpDate = expDate;
            return this;
        }

        public Builder setIsAvailable(boolean isAvailable) {
            mIsAvailable = isAvailable;
            return this;
        }

        public Builder setDoesExpire(boolean doesExpire) {
            mDoesExpire = doesExpire;
            return this;
        }

        public Builder setNumWeeksTilExpire(int numWeeksTilExpire) {
            mNumWeeksTilExpire = numWeeksTilExpire;
            return this;
        }

        public void build() {
            if (mActionType != null) {
                switch (mActionType) {
                    case MEDICATION:
                        mTitleTextView.setTextColor(getResources().getColor(R.color.med_green));
                        break;
                    case SURGICAL:
                        mTitleTextView.setTextColor(getResources().getColor(R.color.sur_blue));
                        break;
                    case CONTINUE:
                        mTitleTextView.setTextColor(getResources().getColor(R.color.con_orange));
                        break;
                }
            }

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

            final ViewTreeObserver vto = mBodyTextView.getViewTreeObserver();
            vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    Layout layout = mBodyTextView.getLayout();
                    if (layout!=null) {
                        int lines = layout.getLineCount();
                        if (lines > 0) {
                            if (layout.getEllipsisCount(lines - 1) > 0) {
                                mLearnMoreTextView.setVisibility(View.VISIBLE);
                            }
                        }
                    }

                    vto.removeOnGlobalLayoutListener(this);
                }
            });
        }
    }

}
