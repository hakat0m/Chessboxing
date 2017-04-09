package com.gumtree.android.post_ad.feature;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;
import com.apptentive.android.sdk.Apptentive;
import com.ebay.classifieds.capi.executor.Result;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ads.PostAdResult;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.events.OnPostAdEvent;
import com.gumtree.android.features.Feature;
import com.gumtree.android.features.Feature.FeatureType;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.managead.PostAdSuccessInfoDialog;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.feature.info.LocalContentInfoFragment;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import com.gumtree.android.post_ad.payment.PostAdPaypalActivity;
import com.gumtree.android.post_ad.summary.PostAdNavigationActivity;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class PostAdPaymentActivity extends PostAdBaseActivity implements OnDismissListener, OnClickListener {
    public static final String EXTRA_PROMOTE_NAV = "com.gumtree.android.post_ad.promote_nav";
    private static final String ONE = "1";
    private static final String POST_AD_INFO_TAG = "POST_AD_INFO_TAG";
    private static final String ZERO = "0";
    @Inject
    AuthBaseStrategy authBaseStrategy;
    private String postedAdId;

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, PostAdPaymentActivity.class);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setContentView(2130903079);
        initUI(bundle);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    @Subscribe
    public void onPostAdEvent(OnPostAdEvent onPostAdEvent) {
        try {
            onComplete(onPostAdEvent.getResult());
        } catch (IllegalStateException e) {
        }
    }

    private void onComplete(Result<PostAdResult> result) {
        if (!result.hasError()) {
            onAdSuccess(result);
        } else if (!this.authBaseStrategy.renewToken(result.getStatusCode(), this)) {
            onErrorPostingAd(result);
        }
    }

    public void onPause() {
        super.onPause();
        this.mEventBus.unregister(this);
    }

    public void onResume() {
        super.onResume();
        this.mEventBus.register(this);
    }

    private void initUI(Bundle bundle) {
        if (getIntent().getBooleanExtra(EXTRA_PROMOTE_NAV, false)) {
            getSupportActionBar().setTitle(2131165925);
        }
        if (bundle == null) {
            addFragment(new PostAdFeaturesFragment(), ZERO, false);
        }
    }

    private void onErrorPostingAd(Result<PostAdResult> result) {
        if (getDao().getPostAdData().isPost()) {
            Apptentive.engage(this, ApptentiveEvent.POST_AD_FAIL.getValue());
        } else {
            Apptentive.engage(this, ApptentiveEvent.EDIT_AD_FAIL.getValue());
        }
        Track.eventPostAdFail(getDao().getPostAdData());
        finish();
        Toast.makeText(this, result.getError().getMessage(), 1).show();
    }

    private void onAdSuccess(Result<PostAdResult> result) {
        this.postedAdId = ((PostAdResult) result.getData()).getAdId();
        if (getDao().getPostAdData().isPost()) {
            Apptentive.engage(this, ApptentiveEvent.POST_AD_SUCCESS.getValue());
            popupPostAdInfo();
            return;
        }
        Apptentive.engage(this, ApptentiveEvent.EDIT_AD_SUCCESS.getValue());
        processSuccessfulPostOrEdit(this.postedAdId);
    }

    private void processSuccessfulPostOrEdit(String str) {
        if (getDao().getPostAdData().isPaidAd()) {
            Track.eventPostAdPaidSuccess(getDao().getPostAdData());
        } else {
            Track.eventPostAdFreeSuccess(getDao().getPostAdData());
            if (getDao().getPostAdData().getSelectedFeatures().size() == 0) {
                PostAdDBIntentService.reset(getDao().getAdId());
                resetDao();
                setResult(-1);
                finish();
                return;
            }
        }
        getDao().getPostAdData().setAdId(str);
        startActivity(PostAdPaypalActivity.createIntent(this, str));
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
        setActionBarTitle(postAdMemDAO);
        String[] strArr = new String[]{ZERO, ONE};
        PostAdData postAdData = postAdMemDAO.getPostAdData();
        if (!postAdData.getAttributeMap().isEmpty()) {
            for (String findFragmentByTag : strArr) {
                Fragment findFragmentByTag2 = getSupportFragmentManager().findFragmentByTag(findFragmentByTag);
                if (findFragmentByTag2 != null) {
                    ((IPostAdDataRefresh) findFragmentByTag2).refreshContent(postAdData);
                }
            }
        }
    }

    private void setActionBarTitle(PostAdMemDAO postAdMemDAO) {
        if (getIntent().getBooleanExtra(EXTRA_PROMOTE_NAV, false)) {
            getSupportActionBar().setTitle(2131165925);
        } else {
            getSupportActionBar().setTitle(postAdMemDAO.isManageAd() ? 2131165846 : 2131165650);
        }
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

    public void onStart() {
        super.onStart();
    }

    public void onStop() {
        super.onStop();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case 2131624914:
                finish();
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + view.getId());
                return;
        }
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showFeature(Feature feature) {
        Fragment newInstance;
        if (FeatureType.TOP_AD.getValue().equals(feature.getName())) {
            newInstance = LocalContentInfoFragment.newInstance(FeatureType.TOP_AD);
        } else if (FeatureType.URGENT.getValue().equals(feature.getName())) {
            newInstance = LocalContentInfoFragment.newInstance(FeatureType.URGENT);
        } else if (FeatureType.BUMP_UP.getValue().equals(feature.getName())) {
            newInstance = LocalContentInfoFragment.newInstance(FeatureType.BUMP_UP);
        } else {
            newInstance = LocalContentInfoFragment.newInstance(FeatureType.HIGHLIGHT);
        }
        addFragment(newInstance, ONE, true);
    }

    private void addFragment(Fragment fragment, String str, boolean z) {
        FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
        if (z) {
            beginTransaction.addToBackStack(str);
        }
        beginTransaction.replace(2131624131, fragment, str);
        beginTransaction.commit();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != LocationActivity.ACTIVITY_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
            }
            PostAdDBIntentService.load(getPostAdId());
            return;
        }
        finish();
    }

    private void popupPostAdInfo() {
        new PostAdSuccessInfoDialog().show(getSupportFragmentManager(), POST_AD_INFO_TAG);
    }

    public void onDismiss(DialogInterface dialogInterface) {
        processSuccessfulPostOrEdit(this.postedAdId);
    }
}
