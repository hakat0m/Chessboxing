package com.gumtree.android.post_ad.steps;

import android.app.Activity;
import android.os.Bundle;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnInsertionFeeUpdateEvent;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.FeatureDurationOption;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.model.PostAdData;
import javax.inject.Inject;

public class PostAdBaseFragment extends BaseFragment {
    @Inject
    EventBus eventBus;
    protected PostAdBaseActivity mActivity;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (PostAdBaseActivity) activity;
    }

    public void onStart() {
        super.onStart();
        this.eventBus.register(this);
    }

    public void onStop() {
        super.onStop();
        this.eventBus.unregister(this);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
    }

    protected void setInsertionFeePrice(Feature feature) {
        boolean z = false;
        if (feature == null || feature.getDurationOptions() == null || feature.getDurationOptions().size() == 0) {
            setPrice(0.0d);
            return;
        }
        FeatureDurationOption featureDurationOption = (FeatureDurationOption) feature.getDurationOptions().get(0);
        if (featureDurationOption == null || featureDurationOption.isInPackage()) {
            setPrice(0.0d);
            return;
        }
        PostAdData postAdData = this.mActivity.getDao().getPostAdData();
        if (Double.parseDouble(featureDurationOption.getPrice()) > 0.0d) {
            z = true;
        }
        postAdData.setIsPaidAd(z);
        setPrice(featureDurationOption.getCurrency() + featureDurationOption.getPrice());
    }

    private void setPrice(double d) {
        this.eventBus.post(new OnInsertionFeeUpdateEvent(getString(2131165842) + AppUtils.round(d)));
    }

    protected void setPrice(String str) {
        this.eventBus.post(new OnInsertionFeeUpdateEvent(str));
    }
}
