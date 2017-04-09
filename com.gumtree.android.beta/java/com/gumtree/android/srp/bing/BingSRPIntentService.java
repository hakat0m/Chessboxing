package com.gumtree.android.srp.bing;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import com.apptentive.android.sdk.BuildConfig;
import com.ebay.classifieds.capi.CapiConfig;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.bing.BingAdPage;
import com.gumtree.android.api.bing.BingAdsApi;
import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.api.bing.BingAdsRequest;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.home.HomeCustomiseFragment;
import javax.inject.Inject;

public class BingSRPIntentService extends BingIntentService {
    private static final String CATEGORY_EXTRA = "CATEGORY_KEY";
    private static final String LOAD_NEXT_PAGE_EXTRA = "LOAD_NEXT_PAGE_KEY";
    private static final String QUERY_EXTRA = "QUERY_KEY";
    private static final String TIMESTAMP_EXTRA = "TIMESTAMP_EXTRA";
    private static final String TRACKING_EXTRA = "TRACKING_KEY";
    @Inject
    CapiConfig apiConfig;
    @Inject
    BingAdsApi bingAdsApi;
    @Inject
    BingSRPCache bingSRPCache;
    private String categoryId;
    @Inject
    EventBus eventBus;
    private String query;
    private String referer;
    private BingAdsRequest request;
    private long timestamp;

    public BingSRPIntentService() {
        super(BingSRPIntentService.class.getSimpleName());
        GumtreeApplication.component().inject(this);
    }

    public static void sendImpressions() {
        Context context = GumtreeApplication.getContext();
        if (BingIntentService.isBingEnabled()) {
            Intent intent = new Intent(context, BingSRPIntentService.class);
            intent.putExtra(TRACKING_EXTRA, true);
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    public static void fetchFirstPage(String str, String str2, long j) {
        Context context = GumtreeApplication.getContext();
        if (BingIntentService.isBingEnabled()) {
            Intent intent = new Intent(context, BingSRPIntentService.class);
            intent.putExtra(QUERY_EXTRA, str);
            intent.putExtra(CATEGORY_EXTRA, str2);
            intent.putExtra(TIMESTAMP_EXTRA, j);
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    public static void fetchNextPage() {
        Context context = GumtreeApplication.getContext();
        if (BingIntentService.isBingEnabled()) {
            Intent intent = new Intent(context, BingSRPIntentService.class);
            intent.putExtra(LOAD_NEXT_PAGE_EXTRA, true);
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    protected void onHandleIntent(Intent intent) {
        if (intent.getBooleanExtra(TRACKING_EXTRA, false)) {
            handleImpressions();
            return;
        }
        if (intent.getBooleanExtra(LOAD_NEXT_PAGE_EXTRA, false)) {
            handleNextPage();
        }
        manageIntent(intent);
    }

    private void handleNextPage() {
        this.request = this.bingSRPCache.nextPageRequest();
        retry(BingSRPIntentService$$Lambda$1.lambdaFactory$(this));
    }

    private void manageIntent(Intent intent) {
        this.query = intent.getStringExtra(QUERY_EXTRA);
        this.categoryId = intent.getStringExtra(CATEGORY_EXTRA);
        this.timestamp = intent.getLongExtra(TIMESTAMP_EXTRA, -1);
        if (this.query != null || this.categoryId != null) {
            if (this.categoryId == null) {
                this.categoryId = BuildConfig.FLAVOR;
            }
            if (this.query == null) {
                this.query = BuildConfig.FLAVOR;
            }
            if (!this.bingSRPCache.isEmpty()) {
                if (!this.bingSRPCache.isSameSRP(this.timestamp)) {
                    handleImpressions();
                    this.bingSRPCache.clear();
                } else {
                    return;
                }
            }
            handleFetchFirstPage();
        }
    }

    private void handleFetchFirstPage() {
        String categories = getCategories(this.categoryId);
        String level1 = getLevel1(categories);
        categories = getAllLevels(categories);
        if (level1 != null) {
            level1 = getString(2131165329, new Object[]{level1});
            Object replace = (this.query + " " + categories).trim().replace(HomeCustomiseFragment.DEFAULT_CATEGORY_PATH, BuildConfig.FLAVOR);
            if (TextUtils.isEmpty(replace)) {
                this.eventBus.post(new OnBingSRPLoadEvent());
                return;
            }
            this.referer = getReferer(this.apiConfig, this.categoryId, this.query);
            this.request = buildBingAdsRequest(level1, replace);
            this.bingSRPCache.setPageRequest(this.request);
            retry(BingSRPIntentService$$Lambda$2.lambdaFactory$(this));
        }
    }

    private boolean fetchResult() {
        try {
            BingAdPage search = this.bingAdsApi.search(this.referer, this.request.getMap());
            if (search == null) {
                search = new BingAdPage();
            }
            this.bingSRPCache.persist(search, this.timestamp);
            this.eventBus.post(new OnBingSRPLoadEvent());
            return true;
        } catch (Throwable e) {
            Log.w(getClass().getSimpleName(), "Avoiding failures, if there is an exception we just don't show ads", e);
            return false;
        }
    }

    private void handleImpressions() {
        retry(BingSRPIntentService$$Lambda$3.lambdaFactory$(this, this.bingSRPCache.getImpressionRequest()));
    }

    /* synthetic */ boolean lambda$handleImpressions$0(BingAdsImpressionRequest bingAdsImpressionRequest) {
        return sendImpression(this.bingAdsApi, bingAdsImpressionRequest);
    }
}
