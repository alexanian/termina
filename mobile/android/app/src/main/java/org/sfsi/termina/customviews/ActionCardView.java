package org.sfsi.termina.customviews;

import android.content.Context;
import android.support.percent.PercentRelativeLayout;
import android.text.Layout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.TextView;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;
import org.sfsi.termina.R;
import org.sfsi.termina.models.OptionDetailsResponse;
import org.sfsi.termina.utils.StringUtils;

/**
 * Created by Sean on 3/4/17.
 */

@EViewGroup(R.layout.action_card_layout)
public class ActionCardView extends PercentRelativeLayout {
    public enum ActionType {
        MEDICATION,
        SURGICAL,
        SURGICAL_TRAVEL,
        LATER_CARE,
        PARENTHOOD,
        ADOPTION,
        CONTINUE }

    @ViewById(R.id.tv_title)
    protected TextView mTitleTextView;

    @ViewById(R.id.tv_subtitle)
    protected TextView mSubtitleTextView;

    @ViewById(R.id.tv_body)
    protected TextView mBodyTextView;

    @ViewById(R.id.tv_learn_more)
    protected TextView mLearnMoreTextView;


    @ViewById(R.id.icon)
    protected TextView mIcon;

    @ViewById(R.id.details_layout)
    protected LinearLayout mDetailsLayout;

    @ViewById(R.id.inner_restriction_layout)
    protected LinearLayout mInnerRestrictionLayout;

    @ViewById(R.id.tv_restrictions_detail)
    protected TextView mRestrictionsDetailTextView;

    @ViewById(R.id.inner_financial_layout)
    protected LinearLayout mInnerFinancialLayout;

    @ViewById(R.id.tv_financial_detail)
    protected TextView mFinancialDetailTextView;

    @ViewById(R.id.inner_resources_layout)
    protected LinearLayout mInnerResourcesLayout;

    @ViewById(R.id.tv_resources_detail)
    protected TextView mResourcesDetailTextView;

    public ActionCardView(final Context context) {
        super(context);
    }

    public ActionCardView(final Context context, final AttributeSet attrs) {
        super(context, attrs);
    }

    public ActionCardView(final Context context, final AttributeSet attrs, final int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public static class Builder {
        Context mContext;

        ActionType mActionType;
        String mTitle;
        String mBody;
        String mExpDate;
        String mCost;
        String mCommon;
        String mInfoLink;
        String mAgeWarning;
        boolean mAgeWarningApplies = false;
        boolean mIsAvailable = true;
        boolean mDoesExpire = false;
        int mNumWeeksSinceExpired = 0;

        boolean mIsShowingDetails = false;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setActionType(String type, OptionDetailsResponse optionDetailsResponse) {
            switch (type) {
                case "medication":
                    mActionType = ActionType.MEDICATION;
                    mTitle = optionDetailsResponse.medication.type;
                    mBody = optionDetailsResponse.medication.description;
                    mCost = optionDetailsResponse.medication.cost;
                    mCommon = optionDetailsResponse.medication.common;
                    mInfoLink = optionDetailsResponse.medication.info_link;
                    break;
                case "surgical":
                    mActionType = ActionType.SURGICAL;
                    mTitle = optionDetailsResponse.surgical.type;
                    mBody = optionDetailsResponse.surgical.description;
                    mCost = optionDetailsResponse.surgical.cost;
                    mCommon = optionDetailsResponse.surgical.common;
                    mInfoLink = optionDetailsResponse.surgical.info_link;
                    break;
                case "surgical_travel":
                    mActionType = ActionType.SURGICAL_TRAVEL;
                    mTitle = optionDetailsResponse.surgical_travel.type;
                    mBody = optionDetailsResponse.surgical_travel.description;
                    mCost = optionDetailsResponse.surgical_travel.cost;
                    mCommon = optionDetailsResponse.surgical_travel.common;
                    mInfoLink = optionDetailsResponse.surgical_travel.info_link;
                    break;
                case "later_care":
                    mActionType = ActionType.LATER_CARE;
                    mTitle = optionDetailsResponse.later_care.type;
                    mBody = optionDetailsResponse.later_care.description;
                    mCost = optionDetailsResponse.later_care.cost;
                    mCommon = optionDetailsResponse.later_care.common;
                    mInfoLink = optionDetailsResponse.later_care.info_link;
                    break;
                case "parenthood":
                    mActionType = ActionType.PARENTHOOD;
                    mTitle = optionDetailsResponse.parenthood.type;
                    mBody = optionDetailsResponse.parenthood.description;
                    mCost = optionDetailsResponse.parenthood.cost;
                    mCommon = optionDetailsResponse.parenthood.common;
                    mInfoLink = optionDetailsResponse.parenthood.info_link;
                    break;
                case "adoption":
                    mActionType = ActionType.ADOPTION;
                    mTitle = optionDetailsResponse.adoption.type;
                    mBody = optionDetailsResponse.adoption.description;
                    mCost = optionDetailsResponse.adoption.cost;
                    mCommon = optionDetailsResponse.adoption.common;
                    mInfoLink = optionDetailsResponse.adoption.info_link;
                    break;

            }
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

        public Builder setAgeWarningApplies(boolean ageWarningApplies, String ageWarning) {
            mAgeWarningApplies = ageWarningApplies;
            mAgeWarning = ageWarning;
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

        public Builder setNumWeeksSinceExpired(int numWeeksSinceExpired) {
            mNumWeeksSinceExpired = numWeeksSinceExpired;
            return this;
        }

        public ActionCardView build() {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            final ActionCardView_ cardView = (ActionCardView_) inflater.inflate(R.layout.action_card_item, null, false);

            if (mActionType != null) {
                switch (mActionType) {
                    case MEDICATION:
                        cardView.mTitleTextView.setTextColor(mContext.getResources().getColor(R.color.med_green));
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_medkit));
                        break;
                    case SURGICAL:
                        cardView.mTitleTextView.setTextColor(mContext.getResources().getColor(R.color.sur_blue));
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_user_md));
                        break;
                    case SURGICAL_TRAVEL:
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_user_md));
                        break;
                    case LATER_CARE:
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_user_md));
                        break;
                    case PARENTHOOD:
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_group));
                        break;
                    case ADOPTION:
                        cardView.mIcon.setText(mContext.getResources().getString(R.string.fa_icon_child));
                        break;
                    case CONTINUE:
                        cardView.mTitleTextView.setTextColor(mContext.getResources().getColor(R.color.con_orange));
                        break;
                }
            }

            if (!TextUtils.isEmpty(mTitle)) {
                cardView.mTitleTextView.setText(mTitle);
            }

            if (mActionType == ActionType.MEDICATION && !mIsAvailable && mNumWeeksSinceExpired > 0) {
                cardView.setBackgroundResource(R.drawable.card_rectangle_red);
                cardView.mTitleTextView.setTextColor(mContext.getResources().getColor(R.color.colorPrimaryDark));
                cardView.mSubtitleTextView.setTextColor(mContext.getResources().getColor(R.color.red));
                cardView.mSubtitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icn_error_red, 0, 0, 0);
                cardView.mSubtitleTextView.setCompoundDrawablePadding((int) mContext.getResources().getDimension(R.dimen.margin_4));
                cardView.mSubtitleTextView.setText(mContext.getResources().getString(R.string.action_card_subtitle_not_available_from, mNumWeeksSinceExpired));
            } else if (mAgeWarningApplies && (mActionType != ActionType.PARENTHOOD && mActionType != ActionType.ADOPTION)) {
                cardView.mSubtitleTextView.setTextColor(mContext.getResources().getColor(R.color.red));
                cardView.mSubtitleTextView.setCompoundDrawablesWithIntrinsicBounds(R.drawable.icn_error_red, 0, 0, 0);
                cardView.mSubtitleTextView.setCompoundDrawablePadding((int) mContext.getResources().getDimension(R.dimen.margin_4));
                cardView.mSubtitleTextView.setText(mContext.getResources().getString(R.string.action_card_restriction));
            } else {
                cardView.mSubtitleTextView.setVisibility(View.GONE);
            }

            if (!TextUtils.isEmpty(mBody)) {
                cardView.mBodyTextView.setText(mBody);
            }

            mIsShowingDetails = false;
            cardView.mDetailsLayout.setVisibility(View.GONE);
            cardView.mLearnMoreTextView.setText(mContext.getResources().getString(R.string.action_card_learn_more));

            cardView.mLearnMoreTextView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View view) {
                    if (mIsShowingDetails) {
                        mIsShowingDetails = false;
                        cardView.mDetailsLayout.setVisibility(View.GONE);
                        cardView.mLearnMoreTextView.setText(mContext.getResources().getString(R.string.action_card_learn_more));

                        cardView.mBodyTextView.setMaxLines(4);
                        cardView.mBodyTextView.setEllipsize(TextUtils.TruncateAt.END);
                    } else {
                        mIsShowingDetails = true;
                        cardView.mDetailsLayout.setVisibility(View.VISIBLE);
                        cardView.mLearnMoreTextView.setText(mContext.getResources().getString(R.string.action_card_learn_less));

                        cardView.mBodyTextView.setMaxLines(Integer.MAX_VALUE);
                        cardView.mBodyTextView.setEllipsize(null);

                        if (mAgeWarningApplies && (mActionType != ActionType.PARENTHOOD && mActionType != ActionType.ADOPTION)) {
                            cardView.mInnerRestrictionLayout.setVisibility(View.VISIBLE);
                            cardView.mRestrictionsDetailTextView.setText(mAgeWarning);
                        } else {
                            cardView.mInnerRestrictionLayout.setVisibility(View.GONE);
                        }

                        if (!TextUtils.isEmpty(mCost)) {
                            cardView.mInnerFinancialLayout.setVisibility(View.VISIBLE);
                            StringUtils.setTextViewHTML(mContext, cardView.mFinancialDetailTextView, mCost);
                        } else {
                            cardView.mInnerFinancialLayout.setVisibility(View.GONE);
                        }

                        if (!TextUtils.isEmpty(mInfoLink)) {
                            cardView.mInnerResourcesLayout.setVisibility(View.VISIBLE);
                            StringUtils.setTextViewHTML(mContext, cardView.mResourcesDetailTextView, mInfoLink);
                        } else {
                            cardView.mInnerResourcesLayout.setVisibility(View.GONE);
                        }
                    }
                }
            });

            return cardView;
        }
    }

}
