package com.gumtree.android.vip.bing;

import android.content.Context;
import android.content.Intent;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.bing.BingAdPage;
import com.gumtree.android.api.bing.BingAdsApi;
import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.api.bing.BingAdsRequest;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.srp.bing.BingIntentService;
import com.gumtree.android.vip.model.Advert;
import javax.inject.Inject;

public class BingVIPIntentService extends BingIntentService {
    private static final String CATEGORY_EXTRA = "CATEGORY_KEY";
    private static final String TITLE_EXTRA = "TITLE_KEY";
    private static final String WEB_URL_EXTRA = "WEB_URL_KEY";
    @Inject
    BingAdsApi bingAdsApi;
    @Inject
    BingVIPCache bingVIPCache;
    @Inject
    EventBus eventBus;
    private BingAdsRequest request;

    public BingVIPIntentService() {
        super(BingVIPIntentService.class.getSimpleName());
        GumtreeApplication.component().inject(this);
    }

    public static void sendImpressions() {
        Context context = GumtreeApplication.getContext();
        if (BingIntentService.isBingEnabled()) {
            Intent intent = new Intent(context, BingVIPIntentService.class);
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    public static void fetchPage(Advert advert) {
        Context context = GumtreeApplication.getContext();
        if (BingIntentService.isBingEnabled()) {
            Intent intent = new Intent(context, BingVIPIntentService.class);
            intent.putExtra(TITLE_EXTRA, advert.getTitle());
            intent.putExtra(CATEGORY_EXTRA, advert.getCategoryId());
            intent.putExtra(WEB_URL_EXTRA, advert.getWebUrl());
            intent.setPackage(context.getPackageName());
            context.startService(intent);
        }
    }

    protected void onHandleIntent(Intent intent) {
        if (intent.hasExtra(TITLE_EXTRA) || intent.hasExtra(CATEGORY_EXTRA)) {
            handleFetch(intent);
        } else {
            handleImpressions();
        }
    }

    private void handleFetch(Intent intent) {
        String stringExtra = intent.getStringExtra(TITLE_EXTRA);
        String stringExtra2 = intent.getStringExtra(CATEGORY_EXTRA);
        String stringExtra3 = intent.getStringExtra(WEB_URL_EXTRA);
        if (!this.bingVIPCache.isSameVIP(stringExtra, stringExtra2)) {
            handleImpressions();
            this.bingVIPCache.clear();
        }
        if (this.bingVIPCache.isEmpty()) {
            this.request = buildBingAdsRequest(getString(2131165330, new Object[]{getLevel1(getCategories(stringExtra2))}), stringExtra);
            retry(BingVIPIntentService$$Lambda$1.lambdaFactory$(this, stringExtra, stringExtra2, stringExtra3));
        }
    }

    /* synthetic */ boolean lambda$handleFetch$0(String str, String str2, String str3) {
        return fetchResult(str, str2, str3);
    }

    private void handleImpressions() {
        retry(BingVIPIntentService$$Lambda$2.lambdaFactory$(this, this.bingVIPCache.getImpressionRequest()));
    }

    /* synthetic */ boolean lambda$handleImpressions$1(BingAdsImpressionRequest bingAdsImpressionRequest) {
        return sendImpression(this.bingAdsApi, bingAdsImpressionRequest);
    }

    private boolean fetchResult(String str, String str2, String str3) {
        try {
            BingAdPage search = this.bingAdsApi.search(str3, this.request.getMap());
            if (search == null) {
                search = new BingAdPage();
            }
            this.bingVIPCache.persist(search, str, str2);
            this.eventBus.post(new OnBingVIPLoadEvent());
            return true;
        } catch (Exception e) {
            Log.w("Avoiding failures, if there is an exception we just don't show ads");
            return false;
        }
    }
}
