package com.gumtree.android.post_ad.feature;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ListView;
import android.widget.TextView;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.FeatureDurationOption;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdAPIIntentService;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.model.PostAdData;
import java.util.ArrayList;
import java.util.Iterator;

public class PostAdFeaturesFragment extends BaseFragment implements IPostAdDataRefresh {
    private FeaturesListAdapter adapter;
    private PostAdBaseActivity mActivity;
    private final OnCheckedChangeListener onCheckedChangeListener = PostAdFeaturesFragment$$Lambda$1.lambdaFactory$(this);
    private final OnClickListener onClickListener = new 2(this);
    private final OnItemSelectedListener onSpinnerItemSelectedListener = new 1(this);
    private TextView totalView;

    /* synthetic */ void lambda$new$0(CompoundButton compoundButton, boolean z) {
        setItemChecked(((Integer) compoundButton.getTag()).intValue(), z);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (PostAdBaseActivity) activity;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setHasOptionsMenu(true);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903202, viewGroup, false);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        enableView(false);
        showProgress(true);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initButtons() {
        Button checkoutButton = getCheckoutButton();
        if (!this.mActivity.getDao().getPostAdData().isPost()) {
            checkoutButton.setText(2131165907);
        }
        checkoutButton.setOnClickListener(PostAdFeaturesFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$initButtons$1(View view) {
        if (this.adapter == null) {
            PostAdAPIIntentService.post(this.mActivity.getDao().getPostAdData());
            return;
        }
        ArrayList selectedFeatures = this.adapter.getSelectedFeatures();
        if (checkInsertionFee()) {
            Feature insertionFee = this.adapter.getInsertionFee();
            ((FeatureDurationOption) insertionFee.getDurationOptions().get(0)).setCheckStatus(true);
            selectedFeatures.add(insertionFee);
        }
        this.mActivity.getDao().getPostAdData().setSelectedFeatures(selectedFeatures);
        PostAdAPIIntentService.post(this.mActivity.getDao().getPostAdData());
        showProgress(true);
        Track.eventPostAdAttempt(this.mActivity.getDao().getPostAdData());
        enableView(false);
    }

    private Button getCheckoutButton() {
        return (Button) getView().findViewById(2131624365);
    }

    private boolean checkInsertionFee() {
        return (this.adapter == null || this.adapter.getInsertionFee() == null || this.adapter.getInsertionFee().isPurchased() || ((FeatureDurationOption) this.adapter.getInsertionFee().getDurationOptions().get(0)).isInPackage()) ? false : true;
    }

    private void initCheckout() {
        if (checkInsertionFee()) {
            FragmentTransaction beginTransaction = getFragmentManager().beginTransaction();
            beginTransaction.replace(2131624812, InsertionFeeHeaderFragment.newInstance(this.adapter.getInsertionFee()), "insertionFee");
            beginTransaction.commitAllowingStateLoss();
            getActionBar().setTitle(this.context.getString(2131165925));
        }
    }

    private void displayTotal(ArrayList<Feature> arrayList, Feature feature) {
        this.mActivity.getDao().getPostAdData().setSelectedFeatures(arrayList);
        this.totalView.setText(this.context.getString(2131165842) + calculateTotal(arrayList, feature));
    }

    private String calculateTotal(ArrayList<Feature> arrayList, Feature feature) {
        Iterator it = arrayList.iterator();
        double d = 0.0d;
        while (it.hasNext()) {
            double d2;
            Feature feature2 = (Feature) it.next();
            FeatureDurationOption hasFeatureDurationOptionChecked = feature2.hasFeatureDurationOptionChecked();
            if (hasFeatureDurationOptionChecked == null || !feature2.isSelected()) {
                d2 = d;
            } else {
                d2 = d + Double.valueOf(hasFeatureDurationOptionChecked.isInPackage() ? new Double(0.0d).doubleValue() : Double.parseDouble(hasFeatureDurationOptionChecked.getPrice())).doubleValue();
            }
            d = d2;
        }
        if (!(feature == null || feature.isPurchased() || ((FeatureDurationOption) feature.getDurationOptions().get(0)).isInPackage())) {
            d += Double.parseDouble(((FeatureDurationOption) feature.getDurationOptions().get(0)).getPrice());
        }
        return AppUtils.round(d);
    }

    private ListView getListView() {
        return (ListView) getView().findViewById(16908298);
    }

    public void setItemChecked(int i, boolean z) {
        if (this.adapter != null) {
            this.adapter.setCheckedItem(i, -1, z);
            displayTotal(this.adapter.getSelectedFeatures(), this.adapter.getInsertionFee());
        }
    }

    private void setSpinnerChecked(FeatureDurationOption featureDurationOption, boolean z) {
        if (this.adapter != null) {
            this.adapter.setCheckedItem(featureDurationOption, z);
            displayTotal(this.adapter.getSelectedFeatures(), this.adapter.getInsertionFee());
        }
    }

    public void refreshContent(PostAdData postAdData) {
        updateUI(postAdData);
    }

    public void enableView(boolean z) {
        getCheckoutButton().setEnabled(z);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        if (this.mActivity.getDao() != null) {
            refreshContent(this.mActivity.getDao().getPostAdData());
        }
    }

    public void showProgress(boolean z) {
        if (getProgressView() != null) {
            getProgressView().setVisibility(z ? 0 : 8);
        }
    }

    private View getProgressView() {
        return getView().findViewById(2131624105);
    }

    private void updateUI(PostAdData postAdData) {
        showProgress(false);
        this.totalView = (TextView) getView().findViewById(2131624473);
        if (getListView().getHeaderViewsCount() <= 0) {
            getListView().addHeaderView(LayoutInflater.from(getActivity()).inflate(2130903332, getListView(), false));
        }
        if (getListView().getFooterViewsCount() <= 0) {
            getListView().addFooterView(LayoutInflater.from(getActivity()).inflate(2130903330, getListView(), false));
        }
        this.adapter = new FeaturesListAdapter(getActivity(), postAdData.getFeaturesResult().getFeatures(), postAdData.getFeaturesResult().getInsertion());
        getListView().setAdapter(this.adapter);
        this.adapter.setOnCheckChangedListener(this.onCheckedChangeListener);
        this.adapter.setOnSpinnerItemSelectedListener(this.onSpinnerItemSelectedListener);
        this.adapter.setOnClickListener(this.onClickListener);
        displayTotal(this.adapter.getSelectedFeatures(), this.adapter.getInsertionFee());
        initCheckout();
        initButtons();
        enableView(true);
    }
}
