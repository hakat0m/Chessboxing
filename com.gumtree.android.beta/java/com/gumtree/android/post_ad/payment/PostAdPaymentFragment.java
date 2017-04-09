package com.gumtree.android.post_ad.payment;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.Toast;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.events.OnOrderCreatedEvent;
import com.gumtree.android.features.OrderConfirmation;
import com.gumtree.android.managead.ManageAdActivity;
import com.gumtree.android.manageads.ManageAdsActivity;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.feature.service.ApplyFeatureIntentService;
import com.gumtree.android.post_ad.feature.service.SubmitOrderIntentService;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.postad.payment.utils.PayPalOrderHelper;
import com.squareup.otto.Subscribe;

public class PostAdPaymentFragment extends BaseFragment implements IPostAdDataRefresh {
    private static final String ORDER = "order";
    private View loadingView;
    private PostAdBaseActivity mActivity;
    private OrderConfirmation order;
    private WebView web;

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mActivity = (PostAdBaseActivity) activity;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (this.web != null) {
            this.web.saveState(bundle);
        }
        bundle.putSerializable(ORDER, this.order);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130903222, viewGroup, false);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                break;
        }
        return true;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.web = (WebView) view.findViewById(2131624535);
        this.loadingView = view.findViewById(2131624467);
        this.web.getSettings().setJavaScriptEnabled(true);
        this.web.setWebViewClient(new PayPalPaymentClient(this, null));
        showLoading(true);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        setHasOptionsMenu(true);
        getActionBar().setTitle(this.context.getString(2131165921));
        if (this.mActivity.getDao() != null) {
            showOrderDetails();
        }
    }

    private void showOrderDetails() {
        showLoading(true);
        SubmitOrderIntentService.submit(this.mActivity.getDao().getPostAdData().getSelectedFeatures(), this.mActivity.getDao().getPostAdData().getAdId());
    }

    public void refreshContent(PostAdData postAdData) {
        showOrderDetails();
    }

    public void enableView(boolean z) {
    }

    @Subscribe
    public void onOrderCreatedEvent(OnOrderCreatedEvent onOrderCreatedEvent) {
        Result order = onOrderCreatedEvent.getOrder();
        if (order.hasError()) {
            onErrorAddingFeatures(order);
        } else {
            gotoPayment(order);
        }
    }

    public void onPause() {
        super.onPause();
        this.mActivity.getEventBus().unregister(this);
    }

    public void onResume() {
        super.onResume();
        this.mActivity.getEventBus().register(this);
    }

    public void onViewStateRestored(Bundle bundle) {
        super.onViewStateRestored(bundle);
        if (bundle != null) {
            this.order = (OrderConfirmation) bundle.getSerializable(ORDER);
            this.web.restoreState(bundle);
        }
    }

    private void loadPayPal() {
        if (this.order.isHtmlPost()) {
            this.web.loadData(this.order.getHtmlForm(GumtreeApplication.getTrackingInfo().getUserAgent()), PayPalOrderHelper.TEXT_HTML_CHARSET_UTF_8, null);
            return;
        }
        this.web.loadUrl(this.order.getPayPalUrl());
    }

    private void showLoading(boolean z) {
        int i;
        int i2 = 8;
        if (z) {
            i = 0;
        } else {
            i = 8;
        }
        if (!z) {
            i2 = 0;
        }
        this.web.setVisibility(i2);
        this.loadingView.setVisibility(i);
    }

    private void success(String str) {
        if (this.mActivity.getDao().getPostAdData().isPaidAd()) {
            if (this.mActivity.getDao().getPostAdData().getSelectedFeatures().size() > 0) {
                Apptentive.engage(getActivity(), ApptentiveEvent.FEATURE_AD_SUCCESS.getValue());
                Track.eventFeaturePaymentSuccess(this.mActivity.getDao().getAdId());
            }
            Apptentive.engage(getActivity(), ApptentiveEvent.POST_AD_SUCCESS.getValue());
        } else {
            Apptentive.engage(getActivity(), ApptentiveEvent.FEATURE_AD_SUCCESS.getValue());
            Track.eventFeaturePaymentSuccess(this.order.getAdId());
        }
        this.order.setPayPalReturnUrl(str);
        ApplyFeatureIntentService.send(this.order);
        PostAdDBIntentService.reset(BuildConfig.FLAVOR);
        this.mActivity.resetDao();
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            startActivity(ManageAdsActivity.createIntent(getActivity()));
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        getActivity().setResult(-1);
        finish();
    }

    private void cancel() {
        if (this.mActivity.getDao().getPostAdData().isPaidAd()) {
            if (this.mActivity.getDao().getPostAdData().getSelectedFeatures().size() > 0) {
                Apptentive.engage(getActivity(), ApptentiveEvent.FEATURE_AD_FAIL.getValue());
                Track.eventFeaturePaymentFail(this.mActivity.getDao().getAdId());
            }
            Track.eventPostAdPaidFail(this.mActivity.getDao().getPostAdData());
        } else {
            Apptentive.engage(getActivity(), ApptentiveEvent.FEATURE_AD_FAIL.getValue());
            Track.eventFeaturePaymentFail(this.order.getAdId());
        }
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            startActivity(ManageAdsActivity.createIntent(getActivity()));
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        getActivity().setResult(-1);
        finish();
    }

    private void onErrorAddingFeatures(Result<OrderConfirmation> result) {
        PostAdDBIntentService.reset(BuildConfig.FLAVOR);
        this.mActivity.resetDao();
        if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
            startActivity(ManageAdsActivity.createIntent(getActivity()));
        } else {
            startActivity(ManageAdActivity.createIntent());
        }
        getActivity().setResult(-1);
        Toast.makeText(this.context, result.getError().getMessage(), 1).show();
        getActivity().finish();
    }

    private void gotoPayment(Result<OrderConfirmation> result) {
        if (result != null && result.getData() != null) {
            if ("paypal".equals(((OrderConfirmation) result.getData()).getProvider())) {
                this.order = (OrderConfirmation) result.getData();
                loadPayPal();
                return;
            }
            ApplyFeatureIntentService.send((OrderConfirmation) result.getData());
            if (DevelopmentFlags.getInstance().isNewManageAdsEnabled()) {
                startActivity(ManageAdsActivity.createIntent(getActivity()));
            } else {
                startActivity(ManageAdActivity.createIntent());
            }
            getActivity().setResult(-1);
            getActivity().finish();
            Toast.makeText(this.context, getString(2131165397), 1).show();
        }
    }
}
