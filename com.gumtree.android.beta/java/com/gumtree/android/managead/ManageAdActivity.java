package com.gumtree.android.managead;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import com.apptentive.android.sdk.Apptentive;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.model.Ads;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import javax.inject.Inject;

public class ManageAdActivity extends NavigationActivity implements AbundanceActivity {
    private static final String BACKSTACK = "backstack";
    private static final String LOADING_TAG = "loading";
    private static final int REQUESTCODE_POSTAD = 1;
    @Inject
    BaseAccountManager accountManager;
    private boolean onStartClearBackstack;
    private long timestamp;

    public static Intent createIntent() {
        Intent intent = new Intent("android.intent.action.EDIT", Ads.URI);
        intent.setFlags(67108864);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        return intent;
    }

    @NonNull
    public static Intent createExplicitIntent(Context context) {
        return new Intent(context, ManageAdActivity.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        ComponentsManager.get().getAppComponent().inject(this);
        setContentView(2130903089, bundle);
        initTimestamp(bundle);
        initState(bundle);
        initClearBackStack(bundle);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755012, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624934:
                Intent createIntent;
                Track.eventMyAdsActionBarPostAd();
                if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
                    createIntent = PostAdActivity.createIntent(this, DraftAd.NEW_AD_ID);
                } else {
                    createIntent = PostAdSummaryActivity.createIntent(this, DraftAd.NEW_AD_ID);
                }
                startActivityForResult(createIntent, REQUESTCODE_POSTAD);
                overridePendingTransition(0, 0);
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private void initClearBackStack(Bundle bundle) {
        if (bundle == null) {
            this.onStartClearBackstack = false;
        } else {
            this.onStartClearBackstack = bundle.getBoolean(BACKSTACK);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(StatefulActivity.EXTRA_SESSION_TIMESTAMP, this.timestamp);
        bundle.putBoolean(BACKSTACK, this.onStartClearBackstack);
    }

    private void initState(Bundle bundle) {
        if (bundle == null && !this.accountManager.isUserLoggedIn()) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                startActivityForResult(AuthActivity.createIntent(AuthIdentifier.MANAGE_AD, this), LocationActivity.ACTIVITY_REQUEST_CODE);
            } else {
                AuthenticatorActivity.startActivity((Activity) this, 2);
            }
        }
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getManageAdsFragment().init(null);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != LocationActivity.ACTIVITY_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                Object[] objArr = new Object[REQUESTCODE_POSTAD];
                objArr[0] = intent.getStringExtra("displayname");
                showSnackBar(getString(2131165552, objArr));
            }
            getManageAdsFragment().init(null);
            return;
        }
        finish();
    }

    private void clearLoading() {
        Fragment findFragmentByTag = getSupportFragmentManager().findFragmentByTag(LOADING_TAG);
        if (findFragmentByTag != null) {
            FragmentManager supportFragmentManager = getSupportFragmentManager();
            FragmentTransaction beginTransaction = supportFragmentManager.beginTransaction();
            beginTransaction.remove(findFragmentByTag);
            beginTransaction.commit();
            supportFragmentManager.popBackStack();
        }
    }

    private void initTimestamp(Bundle bundle) {
        if (bundle == null) {
            this.timestamp = System.currentTimeMillis();
        } else {
            this.timestamp = bundle.getLong(StatefulActivity.EXTRA_SESSION_TIMESTAMP);
        }
    }

    protected void onStart() {
        super.onStart();
        Track.viewMyAds();
        Apptentive.engage(this, "ManageAds");
        onStartClearBackstack();
    }

    private void onStartClearBackstack() {
        if (this.onStartClearBackstack) {
            clearLoading();
            this.onStartClearBackstack = false;
        }
    }

    protected void onPause() {
        super.onPause();
        DeleteAdIntentService.delete(true);
    }

    public void onStop() {
        super.onStop();
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.MANAGE_ADS;
    }

    protected String getTrackingView() {
        return "ManageAds";
    }

    public ManageAdsFragment getManageAdsFragment() {
        return (ManageAdsFragment) getSupportFragmentManager().findFragmentById(2131624160);
    }

    public void setAbundanceInTitle(String str) {
        getSupportActionBar().setSubtitle(Html.fromHtml(str));
    }
}
