package com.gumtree.android.srp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.ad.treebay.TreebayAdManager;
import com.gumtree.android.ad.treebay.TreebayCustomDimensionData;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.common.loaders.SearchSuggestionsInsertTask;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.common.search.Search.SearchType;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.model.Categories;
import com.gumtree.android.savedsearches.SavedSearchesDao;
import com.gumtree.android.savedsearches.SavedSearchesDialogFragment;
import com.gumtree.android.srp.NearByAdvertAdapter.AdItemAnalyticsInfo;
import com.gumtree.android.srp.SRPGridFragment.SRPGridProvider;
import com.gumtree.android.srp.bing.BingSRPIntentService;
import com.gumtree.android.srp.textlinks.TextLinkView;
import com.gumtree.android.vip.VIPActivity;
import com.gumtree.android.vip.bing.BingVIPIntentService;
import javax.inject.Inject;

public class SRPActivity extends NavigationActivity implements LoaderCallbacks<Cursor>, SRPGridProvider {
    private static final String REFRESH_LOCK = "refresh_lock";
    private static final String SORT_TYPE = "sortType";
    @Inject
    BaseAccountManager accountManager;
    private String categoryId;
    private ActionBarDrawerToggle drawerToggle;
    private boolean isRefineDrawerOpenedFromClick;
    private boolean isRefreshLocked;
    private DrawerLayout menuDrawer;
    private SearchRefineFragment refineFragment;
    private int sortSelected;
    private SRPAdViewManager srpAdViewManager = new SRPAdViewManager();
    private SRPContentFragment srpListFragment;
    private long timestamp;
    @Inject
    TrackingTreebayService trackingService;
    @Inject
    TreebayAdManager treebayAdManager;

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        addTimestamp(getIntent(), bundle);
        initRefreshLock(bundle);
        ComponentsManager.get().getAppComponent().inject(this);
        setContentView(2130903114, bundle);
        initUI();
        setMenuState(bundle);
        if (bundle != null) {
            Intent searchIntent = getDefaultSearch().getSearchIntent();
            Bundle bundle2 = bundle.getBundle(StatefulActivity.EXTRA_LATEST_INTENT);
            if (bundle2 != null) {
                searchIntent.putExtras(bundle2);
                setIntent(searchIntent);
            }
        }
        if (bundle == null && DevelopmentFlags.getInstance().isTreeBayEnabled()) {
            this.treebayAdManager.resetManager();
        }
        this.categoryId = getIntent().getStringExtra(StatefulActivity.NAME_CATEGORY_ID);
        loadBingAds();
        init(bundle);
    }

    private void loadBingAds() {
        BingSRPIntentService.fetchFirstPage(getIntent().getStringExtra(StatefulActivity.NAME_QUERY), this.categoryId, getIntent().getLongExtra(StatefulActivity.EXTRA_SESSION_TIMESTAMP, -1));
    }

    protected void onResume() {
        super.onResume();
        this.srpAdViewManager.resume();
        setTitleText();
        BingVIPIntentService.sendImpressions();
    }

    public void onPause() {
        this.srpAdViewManager.pause();
        super.onPause();
    }

    public void onDestroy() {
        if (isFinishing() && DevelopmentFlags.getInstance().isTreeBayEnabled()) {
            this.treebayAdManager.resetManager();
            this.trackingService.clearTreebayTrackedImpressions();
        }
        this.srpAdViewManager.destroy();
        this.menuDrawer.removeDrawerListener(this.drawerToggle);
        super.onDestroy();
    }

    protected void onNewIntent(Intent intent) {
        addTimestamp(intent, null);
        super.onNewIntent(intent);
        setIntent(intent);
        init(null);
        this.categoryId = getIntent().getStringExtra(StatefulActivity.NAME_CATEGORY_ID);
        loadBingAds();
        supportInvalidateOptionsMenu();
        this.refineFragment.doUpdate(intent);
        this.srpListFragment.onActivityNewIntent();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putLong(StatefulActivity.EXTRA_SESSION_TIMESTAMP, this.timestamp);
        bundle.putInt(StatefulActivity.EXTRA_SORT_TYPE, this.sortSelected);
        bundle.putBoolean(REFRESH_LOCK, this.isRefreshLocked);
        bundle.putBundle(StatefulActivity.EXTRA_LATEST_INTENT, getIntent().getExtras());
    }

    public boolean createMenuOptions(Menu menu) {
        getMenuInflater().inflate(2131755017, menu);
        MenuItem findItem = menu.findItem(2131624938);
        MenuItem findItem2 = menu.findItem(2131624939);
        MenuItem findItem3 = menu.findItem(2131624933);
        MenuItem findItem4 = menu.findItem(this.sortSelected);
        MenuItem findItem5 = menu.findItem(2131624945);
        if (findItem4 != null) {
            menu.findItem(this.sortSelected).setChecked(true);
        }
        if (this.menuDrawer != null) {
            if (this.menuDrawer.isDrawerOpen(8388613)) {
                if (findItem != null) {
                    findItem.setVisible(false);
                    findItem2.setVisible(false);
                    findItem5.setVisible(false);
                }
                if (!getResources().getBoolean(2131427332)) {
                    findItem3.setIcon(null);
                }
            } else {
                if (findItem != null) {
                    findItem.setVisible(true);
                    findItem2.setVisible(true);
                    findItem5.setVisible(true);
                }
                hideKeyboard();
            }
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                break;
            case 2131624933:
                this.isRefineDrawerOpenedFromClick = true;
                if (!this.menuDrawer.isDrawerOpen(8388613)) {
                    this.menuDrawer.openDrawer(8388613);
                    break;
                }
                Track.eventSRPFireRefineSearch();
                doRefineSearch();
                this.menuDrawer.closeDrawer(8388613);
                break;
            case 2131624938:
                doSave();
                break;
            default:
                menuItem.setChecked(true);
                doRefine(menuItem.getItemId());
                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.drawerToggle.onConfigurationChanged(configuration);
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == LocationActivity.ACTIVITY_REQUEST_CODE) {
            if (i2 == -1 && intent != null) {
                if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                    if (intent.hasExtra("com.gumtree.android.auth.save_search") && intent.getBooleanExtra("com.gumtree.android.auth.save_search", false)) {
                        savedSearches();
                        if (intent.hasExtra("displayname")) {
                            showSnackBar(getString(2131165553, new Object[]{intent.getStringExtra("displayname")}));
                        }
                    } else if (intent.hasExtra("displayname")) {
                        showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
                    }
                } else if (4 == intent.getIntExtra(AuthenticatorActivity.EXTRA_CALLER, 0)) {
                    savedSearches();
                }
            }
        } else if (i == 12 && i2 == -1) {
            this.srpListFragment.restartLoaders();
        }
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.SEARCH;
    }

    protected String getTrackingView() {
        return "ResultsSearch";
    }

    public void onSearchInvoked() {
        if (!this.menuDrawer.isDrawerOpen(8388613)) {
            this.refineFragment.doUpdate(getIntent());
            this.menuDrawer.openDrawer(8388613);
        }
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this, Categories.URI, null, "_id=?", new String[]{this.categoryId}, null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.srpAdViewManager.prepareAdView(cursor.getString(cursor.getColumnIndex("path_values")), this);
            String stringExtra = getIntent().getStringExtra(StatefulActivity.NAME_QUERY);
            setStickyBannerAdView(stringExtra, this.srpListFragment.getContentUrl());
            if (DevelopmentFlags.getInstance().isTreeBayEnabled()) {
                this.treebayAdManager.fetchAds(this.srpListFragment.getTreebaySearchUrl(), this.srpListFragment.getTreebayItemTemplateUrl());
                Search search = getSearch();
                this.trackingService.setTreebayCustomDimensionData(TreebayCustomDimensionData.builder().categoryId(search.getCategoryId()).categoryName(search.getCategoryName()).locationId(search.getLocationId()).locationName(search.getLocationName()).location(((GumtreeApplication) getApplication()).getGlobalBuyerLocation()).page(getSRPGridFragment().getPageNumber()).searchQuery(stringExtra).build());
                this.trackingService.eventTreebayResultsImpressions(this.treebayAdManager.getSummaryQueueList(), this.treebayAdManager.getUntrackedTreebayItemsPositions());
            }
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public boolean markSelected() {
        return false;
    }

    public boolean isToolBarEnabled() {
        return true;
    }

    public Search getSearch() {
        return Search.create(getApplicationContext(), getIntent());
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
        return false;
    }

    public boolean hideDescription() {
        return false;
    }

    public int getInitialPage() {
        return -1;
    }

    public boolean isScrollingDisabled() {
        return false;
    }

    public int getDefaultMode() {
        return 0;
    }

    public int getNumAds() {
        return 20;
    }

    public boolean hasFooter() {
        return false;
    }

    public void viewAll() {
    }

    public boolean showEndlessProgressLoader() {
        return true;
    }

    public void onRequestComplete() {
        getSupportLoaderManager().restartLoader(0, null, this);
    }

    public PublisherAdView getMpuAdView() {
        return this.srpAdViewManager.getMpuView();
    }

    public PublisherAdView getMpuRowAdView() {
        return this.srpAdViewManager.getMpuRowView();
    }

    public TextLinkView getTextLinkView() {
        return this.srpAdViewManager.getTextLinkView();
    }

    public PublisherAdView getLeaderBoardAdView() {
        return this.srpAdViewManager.getLeaderboardView();
    }

    public PublisherAdRequest getAdRequest(String str, String str2) {
        return this.srpAdViewManager.createRequest(getIntent().getStringExtra(StatefulActivity.NAME_QUERY), str, str2);
    }

    public void onItemSelected(long j, int i, int i2, boolean z, boolean z2, Search search, AdItemAnalyticsInfo adItemAnalyticsInfo) {
        startActivityForResult(VIPActivity.createIntent(this, j, z, z2, search, i, i2), 12);
        if (adItemAnalyticsInfo != null && adItemAnalyticsInfo.isFeatured) {
            Track.eventViewFeaturedAd(adItemAnalyticsInfo.catId, adItemAnalyticsInfo.id);
        }
    }

    public int getSortSelected() {
        return this.sortSelected;
    }

    public void doRefine(int i) {
        switch (i) {
            case 2131624941:
                doSortSearch(Search.SEARCH_DATE_DESCENDING, i);
                return;
            case 2131624942:
                doSortSearch(Search.SEARCH_DISTANCE_ASCENDING, i);
                return;
            case 2131624943:
                doSortSearch(Search.SEARCH_PRICE_ASCENDING, i);
                return;
            case 2131624944:
                doSortSearch(Search.SEARCH_PRICE_DESCENDING, i);
                return;
            default:
                return;
        }
    }

    public void doSave() {
        Track.eventSaveSearchBegin();
        if (this.accountManager.isUserLoggedIn()) {
            savedSearches();
        } else if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SAVE_A_SEARCH, this), LocationActivity.ACTIVITY_REQUEST_CODE);
        } else {
            AuthenticatorActivity.startActivity((Activity) this, 4);
        }
    }

    public boolean areAdsEnabled() {
        return true;
    }

    public void setAbundanceInTitle(String str) {
        if (!isSearch() || !this.menuDrawer.isDrawerOpen(8388613)) {
            getSupportActionBar().setSubtitle(Html.fromHtml(str));
        }
    }

    public boolean isRefreshLocked() {
        return this.isRefreshLocked;
    }

    private void savedSearches() {
        Search search = getSearch();
        long saveSearch = new SavedSearchesDao(getApplicationContext()).saveSearch(search);
        Track.eventSaveSearchSuccess();
        SavedSearchesDialogFragment.newInstance(SavedSearchesDao.convertToUrl(search.getSearchIntent().toUri(0)), saveSearch).show(getSupportFragmentManager(), "DIALOG");
    }

    private void initRefreshLock(Bundle bundle) {
        if (bundle != null) {
            this.isRefreshLocked = bundle.getBoolean(REFRESH_LOCK);
        } else if (isSearch()) {
            this.isRefreshLocked = true;
        } else {
            this.isRefreshLocked = false;
        }
    }

    private void addTimestamp(Intent intent, Bundle bundle) {
        if (bundle == null) {
            this.timestamp = System.currentTimeMillis();
        } else {
            this.timestamp = bundle.getLong(StatefulActivity.EXTRA_SESSION_TIMESTAMP);
        }
        intent.putExtra(StatefulActivity.EXTRA_SESSION_TIMESTAMP, this.timestamp);
    }

    private void setMenuState(Bundle bundle) {
        if (bundle != null) {
            this.sortSelected = ((Integer) bundle.get(StatefulActivity.EXTRA_SORT_TYPE)).intValue();
        } else if (isSearch()) {
            this.menuDrawer.openDrawer(8388613);
        } else {
            this.menuDrawer.closeDrawer(8388613);
        }
    }

    private void init(Bundle bundle) {
        if (bundle == null) {
            initSearchDataIfExists();
        }
        if (this.sortSelected == 0) {
            this.sortSelected = getSortType(getIntent().getStringExtra(SORT_TYPE));
        }
    }

    private int getSortType(String str) {
        int i = 2131624941;
        if (TextUtils.isEmpty(str)) {
            return 2131624941;
        }
        if (str.equals(Search.SEARCH_DATE_DESCENDING)) {
        }
        if (str.equals(Search.SEARCH_DISTANCE_ASCENDING)) {
            i = 2131624942;
        }
        if (str.equals(Search.SEARCH_PRICE_ASCENDING)) {
            i = 2131624943;
        }
        if (str.equals(Search.SEARCH_PRICE_DESCENDING)) {
            return 2131624944;
        }
        return i;
    }

    private void initSearchDataIfExists() {
        if ("android.intent.action.SEARCH".equals(getIntent().getAction())) {
            new SearchSuggestionsInsertTask(this, getIntent().getStringExtra(StatefulActivity.NAME_QUERY)).execute(new Void[0]);
        }
    }

    private void initUI() {
        this.srpListFragment = (SRPContentFragment) getSupportFragmentManager().findFragmentById(2131624130);
        this.refineFragment = (SearchRefineFragment) getSupportFragmentManager().findFragmentById(2131624255);
        this.menuDrawer = (DrawerLayout) findViewById(2131624251);
        this.drawerToggle = new ActionBarDrawerToggle(this, this.menuDrawer, null, 2131165601, 2131165591) {
            public void onDrawerClosed(View view) {
                SRPActivity.this.supportInvalidateOptionsMenu();
                if (SRPActivity.this.isRefreshLocked()) {
                    SRPActivity.this.isRefreshLocked = false;
                    SRPActivity.this.srpListFragment.onActivityNewIntent();
                }
            }

            public void onDrawerOpened(View view) {
                SRPActivity.this.supportInvalidateOptionsMenu();
                if (SRPActivity.this.isRefineDrawerOpenedFromClick) {
                    Track.eventSRPFireRefineBeginButton();
                } else {
                    Track.eventSRPFireRefineBeginSwipe();
                }
                SRPActivity.this.isRefineDrawerOpenedFromClick = false;
            }
        };
        this.menuDrawer.addDrawerListener(this.drawerToggle);
    }

    private void setTitleText() {
        CharSequence stringExtra = getIntent().getStringExtra(StatefulActivity.EXTRA_CATEGORY_NAME);
        String actionbarSubtitle = this.srpListFragment.getActionbarSubtitle();
        getSupportActionBar().setTitle(stringExtra);
        getSupportActionBar().setSubtitle(Html.fromHtml(actionbarSubtitle));
    }

    private boolean isSearch() {
        return getIntent().getAction().equals("android.intent.action.SEARCH");
    }

    protected void doRefineSearch() {
        if (this.sortSelected == 0) {
            this.sortSelected = 2131624941;
        }
        Search create = Search.create(getApplicationContext(), this.refineFragment.getStateBundle());
        create.setType(isSearch() ? SearchType.SEARCH : SearchType.VIEW);
        Search.invoke(this, create);
    }

    private void doSortSearch(String str, int i) {
        this.sortSelected = i;
        Search create = Search.create(getApplicationContext(), this.refineFragment.getStateBundle());
        create.setType(isSearch() ? SearchType.SEARCH : SearchType.VIEW);
        create.addParameter(SORT_TYPE, str);
        Search.invoke(this, create);
    }

    public DrawerLayout getMenuDrawer() {
        return this.menuDrawer;
    }

    private void setStickyBannerAdView(String str, String str2) {
        ViewGroup viewGroup = (ViewGroup) findViewById(2131624252);
        View findViewById = findViewById(2131624253);
        if (getResources().getBoolean(2131427330)) {
            viewGroup.setVisibility(0);
            findViewById.setVisibility(0);
            viewGroup.removeAllViews();
            PublisherAdView stickyBannerView = this.srpAdViewManager.getStickyBannerView();
            if (stickyBannerView != null) {
                viewGroup.addView(stickyBannerView);
                stickyBannerView.setVisibility(0);
                stickyBannerView.loadAd(this.srpAdViewManager.createStickyBannerRequest(str, str2));
                return;
            }
            return;
        }
        viewGroup.setVisibility(8);
        findViewById.setVisibility(8);
    }

    private SRPGridFragment getSRPGridFragment() {
        return (SRPGridFragment) getSupportFragmentManager().findFragmentById(2131624130);
    }
}
