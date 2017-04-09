package com.gumtree.android.post_ad.summary;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.UrlMatcher;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.post_ad.IPostAdDataRefresh;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.PostAdDBIntentService;
import com.gumtree.android.post_ad.model.PostAdData;
import com.gumtree.android.post_ad.model.PostAdMemDAO;
import javax.inject.Inject;

public class PostAdSummaryActivity extends PostAdNavigationActivity {
    public static final String EXTRA_NAV = "com.gumtree.android.post_ad.nav";
    public static final String KEY_PROGRESS = "key_progress";
    private static final String ZERO = "0";
    @Inject
    BaseAccountManager customerAccountManager;
    private boolean isNav;

    public static Intent createIntent(Context context, String str) {
        Intent intent = new Intent(context, PostAdSummaryActivity.class);
        intent.setFlags(67108864);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        return intent;
    }

    public static Intent createIntent(Context context, String str, boolean z, boolean z2) {
        Intent intent = new Intent(context, PostAdSummaryActivity.class);
        intent.setFlags(67108864);
        intent.putExtra(PostAdNavigationActivity.EXTRA_POST_AD_ID, str);
        intent.putExtra(PostAdBaseActivity.EXTRA_RESET, z2);
        intent.putExtra(EXTRA_NAV, z);
        return intent;
    }

    public void onCreate(Bundle bundle) {
        this.isNav = getIntent().getBooleanExtra(EXTRA_NAV, false);
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setContentView(2130903095, bundle);
        initState(bundle);
    }

    protected boolean hasDrawer() {
        return this.isNav;
    }

    public void refreshContent(PostAdMemDAO postAdMemDAO) {
        showProgress(false);
        if (postAdMemDAO != null) {
            Fragment findFragmentByTag;
            getSupportActionBar().setTitle(postAdMemDAO.isManageAd() ? 2131165846 : 2131165650);
            PostAdData postAdData = postAdMemDAO.getPostAdData();
            if (!(postAdData.getAttributeMap().isEmpty() && postAdData.getDefaultPicture() == null)) {
                findFragmentByTag = getSupportFragmentManager().findFragmentByTag(ZERO);
                if (findFragmentByTag != null) {
                    ((IPostAdDataRefresh) findFragmentByTag).refreshContent(postAdData);
                }
            }
            findFragmentByTag = getSupportFragmentManager().findFragmentByTag(UrlMatcher.NO_MATCH);
            if (findFragmentByTag != null) {
                ((IPostAdDataRefresh) findFragmentByTag).refreshContent(postAdData);
            }
            setPostAdButtonState();
        }
    }

    private void setPostAdButtonState() {
        findViewById(2131624913).setEnabled(getDao().isAdValid());
        findViewById(2131624912).setEnabled(true);
        findViewById(2131624528).setEnabled(true);
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
            initState(null);
            loadPostAd(null);
            return;
        }
        finish();
    }

    private void initState(Bundle bundle) {
        if (isLoggedIn()) {
            initUI(bundle);
        } else if (bundle != null) {
        } else {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                startActivityForResult(AuthActivity.createIntent(AuthIdentifier.POST_AD, this), LocationActivity.ACTIVITY_REQUEST_CODE);
            } else {
                AuthenticatorActivity.startActivity((Activity) this, 1);
            }
        }
    }

    private void initUI(Bundle bundle) {
        if (bundle != null) {
            showProgress(bundle.getBoolean(KEY_PROGRESS));
        }
    }

    public void showProgress(boolean z) {
        if (getProgressView() != null) {
            getProgressView().setVisibility(z ? 0 : 8);
        }
    }

    private View getProgressView() {
        return findViewById(2131624105);
    }

    public boolean isLoggedIn() {
        return this.customerAccountManager.isUserLoggedIn();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755013, menu);
        if (isTopLevelPage()) {
            menu.findItem(2131624935).setVisible(true);
        } else {
            menu.findItem(2131624935).setVisible(false);
        }
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624935:
                ((PostAdSummaryFragment) getSupportFragmentManager().findFragmentByTag(ZERO)).reset();
                PostAdDBIntentService.reset(getPostAdId());
                resetDao();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    private boolean isTopLevelPage() {
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    public void onPause() {
        super.onPause();
    }

    public void onBackPressed() {
        if (!isTopLevelPage()) {
            refreshContent(getDao());
        }
        super.onBackPressed();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString(PostAdNavigationActivity.EXTRA_POST_AD_ID, getPostAdId());
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.POST_AD;
    }

    protected String getTrackingView() {
        return "PostAdSummary";
    }

    protected boolean isHomeAsUp() {
        if (isTopLevelPage() && this.isNav) {
            return false;
        }
        return true;
    }
}
