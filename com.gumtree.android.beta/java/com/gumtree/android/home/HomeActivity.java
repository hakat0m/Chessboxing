package com.gumtree.android.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.view.Menu;
import android.view.MenuItem;
import com.apptentive.android.sdk.Apptentive;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.search.keyword.SearchKeywordActivity;
import com.gumtree.android.ad.search.results.SearchResultsActivity;
import com.gumtree.android.api.tracking.ApptentiveEvent;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.clients.SoftUpdateFragment;
import com.gumtree.android.clients.SoftUpdateService;
import com.gumtree.android.clients.model.SoftUpdate;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.common.location.AppLocation;
import com.gumtree.android.common.location.PreferenceBasedRadiusDAO;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.common.views.InnerScrollView;
import com.gumtree.android.common.views.InnerScrollView$ScrollViewListener;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.GumtreeBadgeCounterManagerEvent;
import com.gumtree.android.home.dfp.HomeAdViewManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.message_box.InboxActivity;
import com.gumtree.android.message_box.MessageBadgeDrawable;
import com.gumtree.android.model.Categories;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.postad.services.converter.PaymentConverter;
import com.gumtree.android.srp.NearByAdvertAdapter.AdItemAnalyticsInfo;
import com.gumtree.android.srp.SRPGridFragment;
import com.gumtree.android.srp.SRPGridFragment.SRPGridProvider;
import com.gumtree.android.srp.textlinks.TextLinkView;
import com.gumtree.android.terms.TermsDialog;
import com.gumtree.android.vip.VIPActivity;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;

public class HomeActivity extends NavigationActivity implements InnerScrollView$ScrollViewListener, CustomiseCallback, SRPGridProvider {
    private static final int NUM_ADS = 8;
    @Inject
    BaseAccountManager accountManager;
    private String categoryId;
    private String categoryName;
    private String categoryPath;
    @Inject
    IBadgeCounterManager counterManager;
    @Inject
    EventBus eventBus;
    private HomeAdViewManager homeAdViewManager = new HomeAdViewManager();
    private Long timestamp;
    private LayerDrawable toolbarMessageBadgeDrawable;

    public static Intent createIntent(Context context) {
        Intent intent = new Intent(context, HomeActivity.class);
        intent.addFlags(67108864);
        return intent;
    }

    public static Intent createIntent(String str, Context context) {
        Intent createIntent = createIntent(context);
        createIntent.putExtra("displayname", str);
        return createIntent;
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        promptTerms(bundle);
        requestForSoftUpdateCheck(bundle);
        GumtreeApplication.loadLocalCategoryContent(getApplicationContext());
        if (bundle == null) {
            this.categoryId = PaymentConverter.DEFAULT_PAYMENT_METHOD_ID;
            this.categoryName = HomeCustomiseFragment.DEFAULT_CATEGORY_NAME;
            this.categoryPath = HomeCustomiseFragment.DEFAULT_CATEGORY_PATH;
            this.timestamp = Long.valueOf(System.currentTimeMillis());
            Apptentive.engage(this, ApptentiveEvent.INIT.getValue());
            this.accountManager.initialiseSync();
            showSnackBarIfUserArrivesHereFromLogin();
        } else {
            this.categoryId = bundle.getString("category_pref_id");
            this.categoryName = bundle.getString("category_pref_name");
            this.categoryPath = bundle.getString("category_pref_path");
            this.timestamp = Long.valueOf(bundle.getLong("key_timestamp"));
        }
        setContentView(2130903082, bundle);
        getSRPGridFragment().setMode(1);
        Track.eventAppOpened(GumtreeApplication.isFirstInstall(getApplicationContext()));
    }

    private void showSnackBarIfUserArrivesHereFromLogin() {
        if (getIntent().hasExtra("displayname")) {
            showSnackBar(getString(2131165552, new Object[]{getIntent().getStringExtra("displayname")}));
        }
    }

    private void requestForSoftUpdateCheck(Bundle bundle) {
        if (bundle == null) {
            SoftUpdateService.start(this);
        }
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putString("category_pref_id", this.categoryId);
        bundle.putString("category_pref_name", this.categoryName);
        bundle.putString("category_pref_path", this.categoryPath);
        bundle.putLong("key_timestamp", this.timestamp.longValue());
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        goToDashboard();
        updatePreference();
    }

    private void updatePreference() {
        onCategoryChanged(this.categoryId, this.categoryName, this.categoryPath);
        ((HomeCustomiseFragment) getSupportFragmentManager().findFragmentById(2131624138)).setTitleCustomiseSection(this.categoryName);
    }

    private void promptTerms(Bundle bundle) {
        if (GumtreeApplication.isFirstInstall(getApplicationContext()) && bundle == null) {
            TermsDialog.newInstance().show(getSupportFragmentManager(), "DIALOG");
        }
    }

    public void goToDashboard() {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        for (int i = 1; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            supportFragmentManager.popBackStack(supportFragmentManager.getBackStackEntryAt(i).getId(), 1);
        }
    }

    protected void onStart() {
        super.onStart();
        Track.viewHome(((GumtreeApplication) getApplicationContext()).getGlobalBuyerLocation());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case HighlightView.GROW_NONE /*1*/:
                if (i2 == -1) {
                    onLeafSelect(intent.getStringExtra(StatefulActivity.NAME_CATEGORY_ID), intent.getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME));
                    return;
                }
                return;
            case R.styleable.TextInputLayout_counterOverflowTextAppearance /*9*/:
                if (i2 == -1) {
                    GumtreeApplication.setFirstInstallStatus(false, this);
                    return;
                }
                GumtreeApplication.setFirstInstallStatus(true, this);
                finish();
                return;
            case LocationActivity.ACTIVITY_REQUEST_CODE /*999*/:
                getCustomiseFragment().onActivityResult(i, i2, intent);
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + i);
                return;
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755011, menu);
        this.toolbarMessageBadgeDrawable = (LayerDrawable) menu.findItem(2131624932).getIcon();
        setMenuBadgeCount(this.counterManager.getNumUnreadConversations());
        return super.onCreateOptionsMenu(menu);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624932:
                startActivity(InboxActivity.createIntent(this, InboxActivity.ACTION_BAR));
                break;
            case 2131624933:
                if (!DevelopmentFlags.getInstance().isNewSearchKeywordEnabled()) {
                    onSearchInvoked();
                    Track.sendGAEvent("Home", "HomepageSearchBegin", BuildConfig.FLAVOR);
                    break;
                }
                startActivity(SearchKeywordActivity.createLaunchIntent(this, null));
                break;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onTreeSelect(String str) {
        Intent intent = new Intent("android.intent.action.PICK", Categories.URI);
        intent.putExtra("parent_id", BuildConfig.FLAVOR + str);
        intent.setPackage(GumtreeApplication.getPackageNameForIntent());
        intent.putExtra("flag", true);
        startActivity(intent);
    }

    public void onLeafSelect(String str, String str2) {
        if (DevelopmentFlags.getInstance().isNewSearchResultsEnabled()) {
            startActivity(SearchResultsActivity.createLaunchIntent(this));
            return;
        }
        GumtreeApplication gumtreeApplication = (GumtreeApplication) getApplication();
        AppLocation globalBuyerLocation = gumtreeApplication.getGlobalBuyerLocation();
        Search create = Search.create(gumtreeApplication);
        create.addLocation(globalBuyerLocation);
        create.addRadius(gumtreeApplication, PreferenceBasedRadiusDAO.get(gumtreeApplication), globalBuyerLocation);
        create.addCategory(str + BuildConfig.FLAVOR, str2);
        Search.invoke(this, create);
    }

    protected void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }

    protected void onMenuClose() {
        super.onMenuClose();
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        for (int i = 0; i < supportFragmentManager.getBackStackEntryCount(); i++) {
            if (i > 0) {
                supportFragmentManager.popBackStack();
            }
        }
    }

    @Subscribe
    public void onSoftUpdate(SoftUpdate softUpdate) {
        if (softUpdate.shouldShow()) {
            SoftUpdateFragment.show(softUpdate, this);
        }
    }

    public void onItemSelected(long j, int i, int i2, boolean z, boolean z2, Search search, AdItemAnalyticsInfo adItemAnalyticsInfo) {
        startActivity(VIPActivity.createIntent(this, j, z, z2, search, i, i2));
        if (adItemAnalyticsInfo != null && adItemAnalyticsInfo.isFeatured) {
            Track.eventViewFeaturedAd(adItemAnalyticsInfo.catId, adItemAnalyticsInfo.id);
        }
    }

    public int getSortSelected() {
        return 0;
    }

    public void doRefine(int i) {
    }

    private void doSortSearch(String str) {
    }

    public boolean isRefreshLocked() {
        return false;
    }

    public void setAbundanceInTitle(String str) {
    }

    public boolean createMenuOptions(Menu menu) {
        return false;
    }

    public void doSave() {
    }

    public boolean areAdsEnabled() {
        return true;
    }

    public PublisherAdView getLeaderBoardAdView() {
        return this.homeAdViewManager.getLeaderboardView();
    }

    public PublisherAdRequest getAdRequest(String str, String str2) {
        return this.homeAdViewManager.createRequest(getIntent().getStringExtra(StatefulActivity.NAME_QUERY), str2, str);
    }

    public PublisherAdView getMpuAdView() {
        return this.homeAdViewManager.getMpuView();
    }

    public PublisherAdView getMpuRowAdView() {
        return this.homeAdViewManager.getMpuView();
    }

    public TextLinkView getTextLinkView() {
        return this.homeAdViewManager.getTextLinkView();
    }

    public boolean markSelected() {
        return false;
    }

    public boolean isToolBarEnabled() {
        return false;
    }

    public Search getSearch() {
        AppLocation globalBuyerLocation = ((GumtreeApplication) getApplication()).getGlobalBuyerLocation();
        Search create = Search.create(getApplicationContext());
        create.addLocation(globalBuyerLocation);
        create.addRadius(getApplicationContext(), PreferenceBasedRadiusDAO.get(getApplicationContext()), globalBuyerLocation);
        create.addCategory(this.categoryId, this.categoryName);
        Bundle asBundle = create.asBundle();
        asBundle.putLong(StatefulActivity.EXTRA_SESSION_TIMESTAMP, this.timestamp.longValue());
        return Search.create(getApplicationContext(), asBundle);
    }

    public long getAdId() {
        return 0;
    }

    public int overrideColumnNumber() {
        return 0;
    }

    public int getInitialPosition() {
        return 0;
    }

    public boolean disableGalleryMode() {
        return true;
    }

    public boolean hideDescription() {
        return false;
    }

    public int getInitialPage() {
        return 0;
    }

    public boolean isScrollingDisabled() {
        return true;
    }

    public int getDefaultMode() {
        return 1;
    }

    public int getNumAds() {
        return NUM_ADS;
    }

    public boolean hasFooter() {
        return true;
    }

    public void viewAll() {
        Search.invoke(this, getSearch());
    }

    public boolean showEndlessProgressLoader() {
        return false;
    }

    public void onRequestComplete() {
    }

    public void onScrollChanged(InnerScrollView innerScrollView, int i, int i2, int i3, int i4) {
    }

    private HomeCustomiseFragment getCustomiseFragment() {
        return (HomeCustomiseFragment) getSupportFragmentManager().findFragmentById(2131624138);
    }

    protected void onResume() {
        super.onResume();
        this.eventBus.register(this);
        setMenuBadgeCount(this.counterManager.getNumUnreadConversations());
    }

    public void onCategoryChanged(String str, String str2, String str3) {
        Object obj = null;
        if (!str.equals(this.categoryId)) {
            obj = 1;
        }
        this.categoryId = str;
        this.categoryName = str2;
        this.categoryPath = str3;
        this.homeAdViewManager.prepareAdView(str3, getApplicationContext());
        if (getSRPGridFragment() != null) {
            if (obj != null) {
                this.timestamp = Long.valueOf(System.currentTimeMillis());
                getSRPGridFragment().doServiceCall();
                getSRPGridFragment().restartLoaders();
                return;
            }
            getSRPGridFragment().restartLoaders();
        }
    }

    private SRPGridFragment getSRPGridFragment() {
        return (SRPGridFragment) getSupportFragmentManager().findFragmentById(2131624130);
    }

    public void setMenuBadgeCount(int i) {
        if (this.toolbarMessageBadgeDrawable != null && VERSION.SDK_INT >= 16) {
            Drawable findDrawableByLayerId = this.toolbarMessageBadgeDrawable.findDrawableByLayerId(2131624928);
            findDrawableByLayerId = findDrawableByLayerId instanceof MessageBadgeDrawable ? (MessageBadgeDrawable) findDrawableByLayerId : new MessageBadgeDrawable(this);
            findDrawableByLayerId.setCount(i);
            this.toolbarMessageBadgeDrawable.mutate();
            this.toolbarMessageBadgeDrawable.setDrawableByLayerId(2131624928, findDrawableByLayerId);
        }
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.HOME;
    }

    protected String getTrackingView() {
        return "Home";
    }

    @Subscribe
    public void onGumtreeBadgeCounterManagerEvent(GumtreeBadgeCounterManagerEvent gumtreeBadgeCounterManagerEvent) {
        setMenuBadgeCount(gumtreeBadgeCounterManagerEvent.getNumUnreadConversations());
    }

    protected void onUserAccountCleared() {
    }
}
