package com.gumtree.android.vip.bing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.api.bing.BingAd;
import com.gumtree.android.api.bing.BingAdPage;
import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.srp.bing.BingAdPageWrapper;
import java.util.ArrayList;
import java.util.List;

public class BingVIPCache {
    private boolean adsVisited;
    private final CacheEventListener cacheEventListener;
    private String categoryId;
    private String title;
    private BingAdPageWrapper vipCache;

    public interface CacheEventListener {
        void added();
    }

    public BingVIPCache(@NonNull CacheEventListener cacheEventListener) {
        this.cacheEventListener = cacheEventListener;
    }

    public void clear() {
        this.vipCache = null;
        this.categoryId = null;
        this.title = null;
        this.adsVisited = false;
    }

    @Nullable
    public BingAd get(int i) {
        if (this.vipCache != null && this.vipCache.size() > i) {
            return this.vipCache.get(i);
        }
        return null;
    }

    public void persist(BingAdPage bingAdPage, String str, String str2) {
        this.title = str;
        this.categoryId = str2;
        this.vipCache = new BingAdPageWrapper(bingAdPage);
        this.cacheEventListener.added();
    }

    @Nullable
    public BingAdsImpressionRequest getImpressionRequest() {
        if (this.vipCache == null || this.vipCache.isImpressionsSynched()) {
            return null;
        }
        List arrayList = new ArrayList();
        if (this.adsVisited) {
            arrayList = this.vipCache.getBingAds();
        }
        if (arrayList == null) {
            return null;
        }
        BingAdsImpressionRequest bingAdsImpressionRequest = new BingAdsImpressionRequest();
        bingAdsImpressionRequest.addAll(arrayList);
        bingAdsImpressionRequest.setRguid(this.vipCache.getRguid());
        this.vipCache.setImpressionsSynched(true);
        return bingAdsImpressionRequest;
    }

    public boolean isEmpty() {
        return this.vipCache == null;
    }

    public boolean isSameVIP(String str, String str2) {
        return (this.categoryId + this.title).equals(str2 + str);
    }

    public void setAdsVisited(boolean z) {
        this.adsVisited = z;
    }
}
