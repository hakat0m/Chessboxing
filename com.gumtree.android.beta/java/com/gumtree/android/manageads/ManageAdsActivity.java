package com.gumtree.android.manageads;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.manageads.di.DaggerManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsComponent;
import com.gumtree.android.manageads.di.ManageAdsModule;
import com.gumtree.android.manageads.presenter.ManageAdsNavView;
import com.gumtree.android.manageads.presenter.ManageAdsPresenter;
import com.gumtree.android.manageads.presenter.ManageAdsPresenter.View;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import javax.inject.Inject;

public class ManageAdsActivity extends NavigationActivity implements View {
    public static final String LEARN_MORE_URL = "http://help.gumtree.com/articles/General_Information/Why-was-my-Advert-Removed?at=Where&has&my&ad&gone?&c=All";
    public static final String REFRESH_ADS = "refreshAds";
    public static final int REQUEST_CODE_POST_AD = 1;
    @Bind({2131624162})
    ViewPager manageAdsPager;
    private ManageAdsViewPagerAdapter manageAdsViewPagerAdapter;
    @Inject
    ManageAdsPresenter presenter;
    @Bind({2131624161})
    TabLayout tabs;

    public static Intent createIntent(Context context) {
        Intent createExplicitIntent = createExplicitIntent(context);
        createExplicitIntent.setFlags(67108864);
        return createExplicitIntent;
    }

    public static Intent createExplicitIntent(Context context) {
        return new Intent(context, ManageAdsActivity.class);
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getManageAdsComponent().inject(this);
        setContentView(2130903090, bundle);
        ButterKnife.bind(this);
        initUI();
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        if (intent.getBooleanExtra(REFRESH_ADS, false)) {
            this.presenter.onNewAdFound();
        }
    }

    private void initUI() {
        this.manageAdsViewPagerAdapter = new ManageAdsViewPagerAdapter(this);
        this.manageAdsPager.setAdapter(this.manageAdsViewPagerAdapter);
        this.manageAdsPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                ManageAdsActivity.this.presenter.navigateTo(ManageAdsActivity.this.getNavViewFromPosition(i));
            }

            public void onPageScrollStateChanged(int i) {
            }
        });
        this.tabs.setupWithViewPager(this.manageAdsPager);
    }

    public ManageAdsNavView getNavViewFromPosition(int i) {
        switch (i) {
            case REQUEST_CODE_POST_AD /*1*/:
                return ManageAdsNavView.INACTIVE;
            default:
                return ManageAdsNavView.ACTIVE;
        }
    }

    private ManageAdsComponent getManageAdsComponent() {
        ManageAdsComponent manageAdsComponent = (ManageAdsComponent) ComponentsManager.get().getBaseComponent(ManageAdsComponent.KEY);
        if (manageAdsComponent != null) {
            return manageAdsComponent;
        }
        Object build = DaggerManageAdsComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).manageAdsModule(new ManageAdsModule()).build();
        ComponentsManager.get().putBaseComponent(ManageAdsComponent.KEY, build);
        return build;
    }

    protected void onResume() {
        super.onResume();
        this.presenter.attachView(this);
    }

    protected void onPause() {
        super.onPause();
        this.presenter.detachView();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(ManageAdsComponent.KEY);
            this.presenter.destroy();
        }
        ButterKnife.unbind(this);
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755012, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624934:
                this.presenter.onPostAdClick();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.MANAGE_ADS;
    }

    protected String getTrackingView() {
        return "ManageAds";
    }

    public void showInactiveFragment() {
        this.manageAdsPager.setCurrentItem(ManageAdsNavView.INACTIVE.getValue());
    }

    public void showActiveFragment() {
        this.manageAdsPager.setCurrentItem(ManageAdsNavView.ACTIVE.getValue());
    }

    public void showLoginPage() {
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.MANAGE_AD, this), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity((Activity) this, 2);
        }
    }

    public void showPostAdActivity() {
        Intent createIntent;
        if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
            createIntent = PostAdActivity.createIntent(this, DraftAd.NEW_AD_ID);
        } else {
            createIntent = PostAdSummaryActivity.createIntent(this, DraftAd.NEW_AD_ID);
        }
        startActivity(createIntent);
    }

    public void refreshContent() {
        this.manageAdsViewPagerAdapter.update();
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == LocationActivity.ACTIVITY_REQUEST_CODE) {
            if (i2 != -1) {
                finish();
            } else if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                Object[] objArr = new Object[REQUEST_CODE_POST_AD];
                objArr[0] = intent.getStringExtra("displayname");
                showSnackBar(getString(2131165552, objArr));
            }
        } else if (i == REQUEST_CODE_POST_AD && i2 == -1) {
            this.presenter.onNewAdFound();
        }
    }
}
