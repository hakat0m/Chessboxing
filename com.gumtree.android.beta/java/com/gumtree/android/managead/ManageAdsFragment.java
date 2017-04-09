package com.gumtree.android.managead;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.DevelopmentFlags;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.fragments.BaseListFragment;
import com.gumtree.android.common.http.DetachableResultReceiver;
import com.gumtree.android.common.http.DetachableResultReceiver$Receiver;
import com.gumtree.android.common.utils.AppUtils;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnAdDeletedEvent;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.managead.ManageAdsItemAdapter.OnItemActionClick;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.model.ServiceCallRegistry;
import com.gumtree.android.post_ad.PostAdBaseActivity;
import com.gumtree.android.post_ad.feature.PostAdPaymentActivity;
import com.gumtree.android.post_ad.preview.PreviewActivity;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import uk.co.senab.photoview.IPhotoView;

public class ManageAdsFragment extends BaseListFragment implements LoaderCallbacks<Cursor>, DetachableResultReceiver$Receiver {
    private static final int ABUNDANCE_LOADING = -1;
    private static final String CASE_NOT_SUPPORTED = "case not supported ";
    private static final Handler HANDLER = new Handler();
    private static final String KEY_ID = "key_id";
    private static final int SNACKBAR_DURATION = 5500;
    private int abundance;
    @Inject
    BaseAccountManager accountManager;
    private ManageAdsItemAdapter adapter;
    private EmptyViewStateManager emptyViewManager;
    @Inject
    EventBus eventBus;
    private boolean isInternetAvailable;
    private ManageAdHelper manageAdHelper;
    private final OnItemActionClick onItemActionClick = ManageAdsFragment$$Lambda$1.lambdaFactory$(this);
    private int pageNo;
    private ManageAdsQueryHelper queryHelper;
    private final DetachableResultReceiver receiver = new DetachableResultReceiver(HANDLER);
    private boolean resultReturned;
    private long timestamp;

    /* synthetic */ void lambda$new$0(int i, long j) {
        if (GumtreeApplication.isConnected(getActivity().getApplicationContext())) {
            Intent createIntent;
            switch (i) {
                case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                    Track.eventEditAdBegin(BuildConfig.FLAVOR + j);
                    if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
                        createIntent = PostAdActivity.createIntent(getActivity(), BuildConfig.FLAVOR + j);
                    } else {
                        createIntent = PostAdSummaryActivity.createIntent(getActivity(), BuildConfig.FLAVOR + j, false, true);
                    }
                    startActivity(createIntent);
                    getActivity().overridePendingTransition(0, 0);
                    return;
                case HighlightView.GROW_NONE /*1*/:
                    removeItem(j);
                    return;
                case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                    Track.eventFeaturePaymentBeginManageAds(BuildConfig.FLAVOR + j);
                    createIntent = PostAdPaymentActivity.createIntent(getActivity(), BuildConfig.FLAVOR + j);
                    createIntent.putExtra(PostAdPaymentActivity.EXTRA_PROMOTE_NAV, true);
                    createIntent.putExtra(PostAdBaseActivity.EXTRA_RESET, true);
                    startActivity(createIntent);
                    return;
                case HighlightView.GROW_RIGHT_EDGE /*4*/:
                    Track.eventPayNow();
                    Track.eventFeaturePaymentBeginManageAds(BuildConfig.FLAVOR + j);
                    createIntent = PostAdPaymentActivity.createIntent(getActivity(), BuildConfig.FLAVOR + j);
                    createIntent.putExtra(PostAdBaseActivity.EXTRA_RESET, true);
                    startActivity(createIntent);
                    return;
                case Timber.WARN /*5*/:
                    Track.eventTelecaptureBegin();
                    startActivity(TelecaptureActivity.createIntent(getActivity()));
                    return;
                default:
                    Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + i);
                    return;
            }
        }
        Toast.makeText(this.context, this.context.getString(2131165623), 0).show();
    }

    @Subscribe
    public void onAdDeletedEvent(OnAdDeletedEvent onAdDeletedEvent) {
        init(null);
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.queryHelper = new ManageAdsQueryHelper();
        this.manageAdHelper = new ManageAdHelper();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        setHasOptionsMenu(true);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.emptyViewManager = new EmptyViewStateManager(view.findViewById(2131624470));
        view.findViewById(2131624515).setOnClickListener(ManageAdsFragment$$Lambda$2.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onViewCreated$1(View view) {
        Intent createIntent;
        if (DevelopmentFlags.getInstance().isNewPostAdEnabled()) {
            createIntent = PostAdActivity.createIntent(this.context, DraftAd.NEW_AD_ID);
        } else {
            createIntent = PostAdSummaryActivity.createIntent(this.context, DraftAd.NEW_AD_ID);
        }
        startActivity(createIntent);
        Track.eventMyAdsEmptyPostAd();
    }

    protected int getLayoutId() {
        return 2130903215;
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putInt("key_page_no", this.pageNo);
        bundle.putBoolean("key_is_internet_available", this.isInternetAvailable);
        bundle.putLong("key_timestamp", this.timestamp);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        init(bundle);
        if (bundle != null) {
            getLoaderManager().restartLoader(1, null, this);
            getLoaderManager().restartLoader(0, null, this);
        }
    }

    public void onStart() {
        super.onStart();
        Track.viewResultBrowse(this.pageNo, ((GumtreeApplication) this.context).getGlobalBuyerLocation(), true);
        this.receiver.setReceiver(this);
    }

    public void onResume() {
        super.onResume();
        this.eventBus.register(this);
    }

    public void onPause() {
        super.onPause();
        this.eventBus.unregister(this);
    }

    public void onStop() {
        super.onStop();
        this.receiver.clearReceiver();
        DeleteAdIntentService.delete(false);
    }

    public void onCreateOptionsMenu(Menu menu, MenuInflater menuInflater) {
        menuInflater.inflate(2131755024, menu);
        super.onCreateOptionsMenu(menu, menuInflater);
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 2131624953:
                DeleteAdIntentService.delete(true);
                Track.eventRefreshMyAds();
                setListShown(false);
                this.abundance = ABUNDANCE_LOADING;
                updateActionBarText();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void init(Bundle bundle) {
        if (this.accountManager.isUserLoggedIn()) {
            initVariables(bundle);
            if (bundle == null) {
                setListShown(false);
                doServiceCall();
            }
        }
    }

    private void initAdapter(Cursor cursor) {
        addEndlessFooter();
        this.adapter = new ManageAdsItemAdapter(getActivity(), cursor);
        this.adapter.setOnActionClickListener(this.onItemActionClick);
        setListAdapter(this.adapter);
    }

    private void initVariables(Bundle bundle) {
        if (bundle == null) {
            this.pageNo = ABUNDANCE_LOADING;
            this.isInternetAvailable = true;
            this.timestamp = System.currentTimeMillis();
        } else {
            this.pageNo = bundle.getInt("key_page_no");
            this.isInternetAvailable = bundle.getBoolean("key_is_internet_available");
            this.timestamp = bundle.getLong("key_timestamp");
        }
        this.abundance = ABUNDANCE_LOADING;
        this.resultReturned = true;
    }

    public void onListItemClick(ListView listView, View view, int i, long j) {
        super.onListItemClick(listView, view, i, j);
        Intent newInstance = PreviewActivity.newInstance(getActivity(), BuildConfig.FLAVOR + j);
        newInstance.putExtra(PostAdBaseActivity.EXTRA_RESET, true);
        startActivity(newInstance);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return new CursorLoader(this.context, this.queryHelper.getUri(), null, this.queryHelper.getWhereQuery(), this.queryHelper.getWhereParams(new String[]{String.valueOf(this.timestamp)}), this.queryHelper.getSortOrder());
            case HighlightView.GROW_NONE /*1*/:
                return new CursorLoader(this.context, ServiceCallRegistry.URI, null, "url=?", new String[]{getUrl()}, null);
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + i);
                return null;
        }
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                if (this.adapter == null) {
                    initAdapter(cursor);
                } else {
                    this.adapter.changeCursor(cursor);
                }
                setEndlessListViewState();
                EmptyViewStateManager.access$000(this.emptyViewManager, this.adapter.getCount(), this.abundance, this.isInternetAvailable);
                setListShown(this.resultReturned);
                return;
            case HighlightView.GROW_NONE /*1*/:
                this.abundance = getAbundanceData(cursor);
                updateActionBarText();
                getLoaderManager().destroyLoader(1);
                setEndlessListViewState();
                if (this.adapter != null) {
                    EmptyViewStateManager.access$000(this.emptyViewManager, this.adapter.getCount(), this.abundance, this.isInternetAvailable);
                    return;
                }
                return;
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + loader.getId());
                return;
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    private void setEndlessListViewState() {
        if (this.adapter != null) {
            if (this.adapter.getCount() >= this.abundance || !this.isInternetAvailable) {
                removeEndlessFooter();
            } else {
                addEndlessFooter();
            }
        }
    }

    private int getAbundanceData(Cursor cursor) {
        if (cursor.moveToFirst()) {
            return cursor.getInt(cursor.getColumnIndex("abundance"));
        }
        return ABUNDANCE_LOADING;
    }

    private void updateActionBarText() {
        String textForAbundance = getTextForAbundance();
        FragmentActivity activity = getActivity();
        if (activity instanceof AbundanceActivity) {
            ((AbundanceActivity) activity).setAbundanceInTitle(textForAbundance);
        }
    }

    private String getTextForAbundance() {
        if (!this.isInternetAvailable) {
            return getEmptyText();
        }
        if (this.abundance == ABUNDANCE_LOADING) {
            return this.context.getString(2131165866);
        }
        return "<b>" + AppUtils.getFormattedNumber(this.abundance) + "</b>" + " " + getResources().getQuantityString(2131689472, this.abundance);
    }

    private ManageAdsUrlHelper getURLHelper() {
        return new ManageAdsUrlHelper(this.pageNo, this.timestamp, this.receiver);
    }

    public String getUrl() {
        return getURLHelper().getIntentForServiceCall().getDataString();
    }

    protected String getEmptyText() {
        return BuildConfig.FLAVOR;
    }

    public void onReceiveResult(int i, Bundle bundle) {
        setInternetAvailableState(i);
        this.resultReturned = true;
        if (this.pageNo == 0) {
            getLoaderManager().restartLoader(0, null, this);
            getLoaderManager().restartLoader(1, null, this);
        }
    }

    private void setInternetAvailableState(int i) {
        this.isInternetAvailable = i == IPhotoView.DEFAULT_ZOOM_DURATION;
    }

    protected void onScrollToEnd() {
        super.onScrollToEnd();
        if (this.resultReturned && areItemsAvailableToLoad()) {
            doServiceCall();
        }
    }

    private boolean areItemsAvailableToLoad() {
        return this.adapter.getCount() < this.abundance;
    }

    private void doServiceCall() {
        if (this.pageNo == ABUNDANCE_LOADING) {
            this.abundance = ABUNDANCE_LOADING;
            updateActionBarText();
        }
        this.pageNo++;
        redoServiceCall();
    }

    private void redoServiceCall() {
        this.resultReturned = false;
        startService(getURLHelper().getIntentForServiceCall());
    }

    private void removeItem(long j) {
        new Bundle().putLong(KEY_ID, j);
        this.manageAdHelper.editItem(this.context, j, 1);
        Snackbar.make(getView(), 2131165639, SNACKBAR_DURATION).setAction(2131165906, ManageAdsFragment$$Lambda$3.lambdaFactory$(this, j)).setActionTextColor(ContextCompat.getColor(this.context, 2131493375)).show();
        this.abundance += ABUNDANCE_LOADING;
        updateActionBarText();
        getLoaderManager().restartLoader(0, null, this);
    }

    /* synthetic */ void lambda$removeItem$2(long j, View view) {
        this.manageAdHelper.editItem(this.context, j, 0);
        this.abundance++;
        updateActionBarText();
        getLoaderManager().restartLoader(0, null, this);
    }
}
