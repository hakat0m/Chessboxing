package com.gumtree.android.srp;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.MenuItemCompat;
import android.text.Html;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.google.android.gms.ads.doubleclick.PublisherAdRequest;
import com.google.android.gms.ads.doubleclick.PublisherAdView;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.common.activities.header.HeaderActionBar;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseGridFragment;
import com.gumtree.android.common.http.DetachableResultReceiver;
import com.gumtree.android.common.http.DetachableResultReceiver$Receiver;
import com.gumtree.android.common.search.Search;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.common.utils.Counters;
import com.gumtree.android.common.views.LastSelectedAware;
import com.gumtree.android.common.views.StatefulPinnedGridView;
import com.gumtree.android.common.views.ToggleTabActionProvider;
import com.gumtree.android.common.views.ToggleTabView.OnTabSelectListener;
import com.gumtree.android.favourites.FavouriteIntentService;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.ServiceCallRegistry;
import com.gumtree.android.srp.NearByAdvertAdapter.AdItemAnalyticsInfo;
import com.gumtree.android.srp.textlinks.TextLinkView;
import uk.co.senab.photoview.IPhotoView;

public class SRPGridFragment extends BaseGridFragment implements LoaderCallbacks<Cursor>, DetachableResultReceiver$Receiver, SRPContentFragment {
    private static final Handler HANDLER = new Handler();
    public static final int MODE_GRID = 1;
    private static final String SPACE = " ";
    private static IntentFilter mFavIntentFilter = new IntentFilter(FavouriteIntentService.ACTION_AD_FAVOURITED);
    private NearByAdvertAdapter adapter;
    private String contentUrl;
    private Counters counters;
    private boolean cursorUpdated;
    private SRPGridProvider dataProvider;
    private final OnFavouriteClickListener favouriteClickListener = new 2(this);
    private Boolean footerActionBarVisibility;
    private HeaderActionBar headerView;
    private boolean httpFailure;
    private BroadcastReceiver mFavBroadcastReceiver = new 1(this);
    private int mode;
    private final OnTabSelectListener onTabSelectListener = new 3(this);
    private int pageNumber;
    private final DetachableResultReceiver receiver = new DetachableResultReceiver(HANDLER);
    private boolean resultReturned;
    private int sortState;
    private boolean toastDisplayed;
    private String treebayItemTemplateUrl;
    private String treebaySearchUrl;

    public interface SRPGridProvider {
        boolean areAdsEnabled();

        boolean createMenuOptions(Menu menu);

        boolean disableGalleryMode();

        void doRefine(int i);

        void doSave();

        long getAdId();

        PublisherAdRequest getAdRequest(String str, String str2);

        int getDefaultMode();

        int getInitialPage();

        int getInitialPosition();

        PublisherAdView getLeaderBoardAdView();

        PublisherAdView getMpuAdView();

        PublisherAdView getMpuRowAdView();

        int getNumAds();

        Search getSearch();

        int getSortSelected();

        TextLinkView getTextLinkView();

        boolean hasFooter();

        boolean hideDescription();

        boolean isRefreshLocked();

        boolean isScrollingDisabled();

        boolean isToolBarEnabled();

        boolean markSelected();

        void onItemSelected(long j, int i, int i2, boolean z, boolean z2, Search search, AdItemAnalyticsInfo adItemAnalyticsInfo);

        void onRequestComplete();

        int overrideColumnNumber();

        void setAbundanceInTitle(String str);

        boolean showEndlessProgressLoader();

        void viewAll();
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.receiver.setReceiver(this);
        if (activity instanceof SRPGridProvider) {
            this.dataProvider = (SRPGridProvider) activity;
            boolean z = this.dataProvider.isToolBarEnabled() && getResources().getBoolean(2131427331);
            this.footerActionBarVisibility = Boolean.valueOf(z);
            return;
        }
        throw new RuntimeException("Activity using SRPFragment need to implement SRPGridDataProvider");
    }

    public void onStart() {
        super.onStart();
        if (this.headerView != null) {
            this.headerView.setOnTabSelectListener(this.onTabSelectListener);
        }
    }

    public void onStop() {
        super.onStop();
        if (this.headerView != null) {
            this.headerView.removeOnTabSelectListener();
        }
    }

    public void onResume() {
        super.onResume();
        getActivity().registerReceiver(this.mFavBroadcastReceiver, mFavIntentFilter);
    }

    public void onPause() {
        super.onPause();
        getActivity().unregisterReceiver(this.mFavBroadcastReceiver);
    }

    public void onDetach() {
        super.onDetach();
        this.receiver.clearReceiver();
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putSerializable("key_abundance", this.counters);
        bundle.putInt("key_page_no", this.pageNumber);
        bundle.putInt("key_selection_sort", this.dataProvider.getSortSelected());
        bundle.putInt("key_selection_view", getMode());
        bundle.putBoolean("key_is_internet_available", this.httpFailure);
    }

    private void init(Bundle bundle) {
        initVariable(bundle);
        initServiceCall(bundle);
    }

    private void initServiceCall(Bundle bundle) {
        if (bundle == null) {
            doServiceCall();
        } else {
            restartLoaders();
        }
    }

    private void initAdapter() {
        if (this.dataProvider.markSelected() && (getGridView() instanceof LastSelectedAware)) {
            ((LastSelectedAware) getGridView()).setLastSelectedId(this.dataProvider.getAdId());
        }
        int overrideColumnNumber = this.dataProvider.overrideColumnNumber();
        if (overrideColumnNumber > 0) {
            getGridView().setNumColumns(overrideColumnNumber);
        }
        this.adapter = new 4(this, getActivity(), null, getGridView(), getMode());
        this.adapter.setListener(this.favouriteClickListener);
        this.adapter.setNearByHeader(this.counters);
        setGridAdapter(this.adapter);
        overrideColumnNumber = this.dataProvider.getInitialPosition();
        if (overrideColumnNumber > 0) {
            getGridView().setSelection(overrideColumnNumber);
        }
    }

    private void hackToCheckNearByCountOnEveryPageRequest() {
        getLoaderManager().restartLoader(MODE_GRID, null, this);
    }

    private void initVariable(Bundle bundle) {
        this.cursorUpdated = true;
        if (bundle == null) {
            this.counters = null;
            this.pageNumber = this.dataProvider.getInitialPage();
            this.sortState = getSortType(getIntent().getStringExtra(Search.SORT_TYPE));
            this.httpFailure = false;
            setMode(PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).getInt("pref_srp_view", this.dataProvider.getDefaultMode()));
        } else {
            this.httpFailure = bundle.getBoolean("key_is_internet_available");
            this.counters = (Counters) bundle.getSerializable("key_abundance");
            this.pageNumber = bundle.getInt("key_page_no");
            this.sortState = bundle.getInt("key_selection_sort");
            setMode(bundle.getInt("key_selection_view"));
        }
        this.resultReturned = true;
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

    protected int getLayoutId() {
        return 2130903238;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        init(bundle);
        initLoaders();
        this.headerView = (HeaderActionBar) view.findViewById(2131624597);
        this.headerView.initialize(this.sortState);
        if (this.footerActionBarVisibility.booleanValue()) {
            view.findViewById(2131624599).setVisibility(0);
            this.headerView.setVisibility(0);
            this.headerView.getSaveButton().setOnClickListener(SRPGridFragment$$Lambda$1.lambdaFactory$(this));
            this.headerView.getSortButton().setOnClickListener(SRPGridFragment$$Lambda$2.lambdaFactory$(this));
        } else {
            this.headerView.setVisibility(8);
            view.findViewById(2131624599).setVisibility(8);
        }
        StatefulPinnedGridView statefulPinnedGridView = (StatefulPinnedGridView) getGridView();
        setGridModeOnlyIfGalleryModeEnabled(getMode());
        statefulPinnedGridView.setExpanded(this.dataProvider.isScrollingDisabled());
        statefulPinnedGridView.invalidateViews();
        if (this.httpFailure) {
            showCowsOnFirstEntryWithNoInternet();
        }
        initAdapter();
        this.headerView.getToggleTabView().checkItem(getMode(), true);
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.dataProvider.doSave();
    }

    /* synthetic */ void lambda$onViewCreated$1(View view) {
        onSort(this.headerView.getSortButton().getChildAt(0));
    }

    private void setGridModeOnlyIfGalleryModeEnabled(int i) {
        StatefulPinnedGridView statefulPinnedGridView = (StatefulPinnedGridView) getGridView();
        if (this.dataProvider.disableGalleryMode()) {
            statefulPinnedGridView.setMode(this.dataProvider.getDefaultMode());
        }
        statefulPinnedGridView.setMode(i);
    }

    public void restartLoaders() {
        if (isAdded()) {
            getLoaderManager().restartLoader(MODE_GRID, null, this);
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    public String getContentUrl() {
        return this.contentUrl;
    }

    public String getTreebaySearchUrl() {
        return this.treebaySearchUrl;
    }

    public String getTreebayItemTemplateUrl() {
        return this.treebayItemTemplateUrl;
    }

    public void initLoaders() {
        getLoaderManager().initLoader(MODE_GRID, null, this);
        getLoaderManager().initLoader(0, null, this);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        this.dataProvider.createMenuOptions(menu);
        MenuItem findItem = menu.findItem(2131624945);
        if (findItem != null) {
            ToggleTabActionProvider toggleTabActionProvider = (ToggleTabActionProvider) MenuItemCompat.getActionProvider(findItem);
            toggleTabActionProvider.setCheckItem(getMode());
            toggleTabActionProvider.setTabSelectListener(this.onTabSelectListener);
        }
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public void onDestroyOptionsMenu() {
        super.onDestroyOptionsMenu();
    }

    public void onCreate(Bundle bundle) {
        setHasOptionsMenu(true);
        super.onCreate(bundle);
    }

    public void onGridItemClick(GridView gridView, View view, int i, long j) {
        super.onGridItemClick(gridView, view, i, j);
        try {
            GridView gridView2 = getGridView();
            if (gridView2 instanceof LastSelectedAware) {
                ((LastSelectedAware) gridView2).setLastSelectedId(j);
                this.adapter.notifyDataSetChanged();
            }
            int convertPositionForWrappeedAdapter = this.adapter.convertPositionForWrappeedAdapter(i);
            boolean z = false;
            if (this.counters != null) {
                z = this.counters.isNearBy(convertPositionForWrappeedAdapter);
            }
            this.dataProvider.onItemSelected(j, convertPositionForWrappeedAdapter, this.pageNumber, z, isListView(), this.dataProvider.getSearch(), this.adapter.getInfoForAnalytics(convertPositionForWrappeedAdapter));
        } catch (IllegalStateException e) {
        }
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] strArr;
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                strArr = new String[MODE_GRID];
                strArr[0] = this.dataProvider.getSearch().getTimestamp() + BuildConfig.FLAVOR;
                SRPQueryHelper sRPQueryHelper = new SRPQueryHelper();
                return new CursorLoader(this.context, sRPQueryHelper.getUri(), sRPQueryHelper.getProjections(), sRPQueryHelper.getWhereQuery(), sRPQueryHelper.getWhereParams(strArr), sRPQueryHelper.getSortOrder());
            case MODE_GRID /*1*/:
                strArr = new String[MODE_GRID];
                strArr[0] = getCall().getDataString();
                return new CursorLoader(this.context, ServiceCallRegistry.URI, null, "url=?", strArr, null);
            default:
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                if (this.resultReturned) {
                    getLoadingView().setVisibility(8);
                    hideEndlessFooter();
                    this.cursorUpdated = true;
                }
                loadList(cursor);
                return;
            case MODE_GRID /*1*/:
                setAbundanceData(cursor);
                setActionBarText();
                showNoAdsView();
                return;
            default:
                Log.e(getClass().getSimpleName(), "case not supported " + loader.getId());
                return;
        }
    }

    private void showNoAdsView() {
        getView().findViewById(16908309).setVisibility(getTotalAbundanceCount() == 0 ? 0 : 8);
        if (VERSION.SDK_INT > 10) {
            ((TextView) getView().findViewById(16908309)).setText(Html.fromHtml(getResources().getString(2131165621)));
        }
    }

    private int getTotalAbundanceCount() {
        if (this.counters == null) {
            return -1;
        }
        return this.counters.getTotal();
    }

    protected void loadList(Cursor cursor) {
        if (cursor != null) {
            this.adapter.changeCursor(cursor, this.counters);
            boolean z = this.resultReturned || isGridViewVisible() || cursor.getCount() > 0;
            setGridShown(z);
        }
    }

    private void setAbundanceData(Cursor cursor) {
        if (cursor != null && !cursor.isClosed() && cursor.moveToFirst()) {
            int i = cursor.getInt(cursor.getColumnIndex("abundance"));
            int i2 = cursor.getInt(cursor.getColumnIndex("additional_abundance"));
            int i3 = cursor.getInt(cursor.getColumnIndex("top_ad_abundance"));
            this.contentUrl = cursor.getString(cursor.getColumnIndex("content_url"));
            this.treebaySearchUrl = cursor.getString(cursor.getColumnIndex("treebay_search_url"));
            this.treebayItemTemplateUrl = cursor.getString(cursor.getColumnIndex("treebay_item_template_url"));
            if (this.adapter != null) {
                this.counters = new Counters(i, i3, i2, this.contentUrl);
                this.adapter.setNearByHeader(this.counters);
            }
        }
    }

    private void setActionBarText() {
        if (this.resultReturned) {
            this.dataProvider.setAbundanceInTitle(getTextForAbundance());
            this.dataProvider.onRequestComplete();
        }
    }

    private String getTextForAbundance() {
        if (this.counters == null) {
            return this.context.getString(2131165866);
        }
        return "<b>" + AppUtils.getFormattedNumber(this.counters.getOrganicAdCount()) + "</b>" + SPACE + getString(2131165815) + SPACE + getString(2131165861) + SPACE + this.dataProvider.getSearch().getStringValue(StatefulActivity.EXTRA_LOCATION_NAME);
    }

    private void showToastOnNoInternetAndPaging() {
        if (!this.toastDisplayed) {
            Toast.makeText(this.context, getString(2131165854), 0).show();
        }
    }

    private void showCowsOnFirstEntryWithNoInternet() {
        getEmptyTextView().setBackgroundResource(2130837646);
        getGridView().setVisibility(8);
    }

    private void removeCowsOnFirstEntryWithNoInternet() {
        getEmptyTextView().setBackgroundResource(17170445);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    protected String getEmptyText() {
        return BuildConfig.FLAVOR;
    }

    public void onActivityNewIntent() {
        setGridShown(false);
        init(null);
        resetScrollPosition();
        restartLoaders();
    }

    public String getActionbarSubtitle() {
        if (isAdded()) {
            return getTextForAbundance();
        }
        return BuildConfig.FLAVOR;
    }

    public void onReceiveResult(int i, Bundle bundle) {
        if (i == IPhotoView.DEFAULT_ZOOM_DURATION && !isGridViewVisible()) {
            setGridShown(true);
            this.httpFailure = false;
            removeCowsOnFirstEntryWithNoInternet();
            this.cursorUpdated = true;
            hideEndlessFooter();
            getLoadingView().setVisibility(8);
        }
        if (i != IPhotoView.DEFAULT_ZOOM_DURATION) {
            this.httpFailure = true;
            if (NearByAdvertAdapter.isContentAvailable(getGridView())) {
                if (!this.toastDisplayed) {
                    showToastOnNoInternetAndPaging();
                }
                this.toastDisplayed = true;
            } else {
                showCowsOnFirstEntryWithNoInternet();
            }
            this.cursorUpdated = true;
            hideEndlessFooter();
            getLoadingView().setVisibility(8);
        }
        this.resultReturned = true;
    }

    protected void onScrollToEnd() {
        if (!this.dataProvider.isScrollingDisabled()) {
            if ((getTotalAbundanceCount() < 0 || !NearByAdvertAdapter.isAdLimitReached(this.pageNumber, getTotalAbundanceCount())) && this.resultReturned) {
                doServiceCall();
            }
        }
    }

    public void doServiceCall() {
        if (this.resultReturned && this.cursorUpdated && !this.dataProvider.isRefreshLocked()) {
            Search search = this.dataProvider.getSearch();
            if (isValidSearch(search)) {
                this.resultReturned = false;
                this.cursorUpdated = false;
                this.pageNumber += MODE_GRID;
                if (this.dataProvider.showEndlessProgressLoader()) {
                    showEndlessFooter();
                }
                startService(getCall(search));
                if (!isHomeActivityThenAvoidTracking()) {
                    Track.viewResultBrowse(this.pageNumber, ((GumtreeApplication) this.context).getGlobalBuyerLocation(), isListView(), search);
                }
            }
        }
    }

    private boolean isHomeActivityThenAvoidTracking() {
        return getActivity().getClass().getSimpleName().equals(HomeActivity.class.getSimpleName());
    }

    private boolean isValidSearch(Search search) {
        return (search == null || TextUtils.isEmpty(search.getCategoryId())) ? false : true;
    }

    private Intent getCall() {
        return getCall(this.dataProvider.getSearch());
    }

    private Intent getCall(Search search) {
        return SearchUrl.from(search).getCall(this.pageNumber, this.dataProvider.getNumAds(), this.receiver);
    }

    private void showEndlessFooter() {
        if (this.pageNumber > 0) {
            getView().findViewById(2131624598).setVisibility(0);
        }
    }

    private void hideEndlessFooter() {
        getView().findViewById(2131624598).setVisibility(8);
    }

    private void onSort(View view) {
        PopupMenu popupMenu = new PopupMenu(getActivity(), view);
        popupMenu.getMenuInflater().inflate(2131755028, popupMenu.getMenu());
        popupMenu.getMenu().findItem(this.headerView.getSortSelected()).setChecked(true);
        popupMenu.setOnMenuItemClickListener(SRPGridFragment$$Lambda$3.lambdaFactory$(this));
        popupMenu.show();
    }

    /* synthetic */ boolean lambda$onSort$2(MenuItem menuItem) {
        this.headerView.setSortSelected(menuItem.getItemId());
        this.dataProvider.doRefine(menuItem.getItemId());
        return true;
    }

    private int getMode() {
        if (this.dataProvider.disableGalleryMode()) {
            return this.dataProvider.getDefaultMode();
        }
        return this.mode;
    }

    public void setMode(int i) {
        boolean z;
        boolean z2 = true;
        this.mode = i;
        GridView gridView = getGridView();
        if (i != MODE_GRID) {
            z = true;
        } else {
            z = false;
        }
        gridView.setFocusableInTouchMode(z);
        GridView gridView2 = getGridView();
        if (i == MODE_GRID) {
            z2 = false;
        }
        gridView2.setFocusable(z2);
    }

    private void setModeAndUpdatePreference(int i) {
        setMode(i);
        Track.eventChangeViewSrp(getMode());
        PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext()).edit().putInt("pref_srp_view", getMode()).commit();
    }

    public int getPageNumber() {
        return this.pageNumber;
    }

    public boolean isListView() {
        return getMode() == 0;
    }
}
