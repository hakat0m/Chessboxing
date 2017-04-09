package com.gumtree.android.srp.bing;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.api.bing.BingAd;
import com.gumtree.android.api.bing.BingAdPage;
import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.api.bing.BingAdsRequest;
import java.util.LinkedList;
import java.util.List;

public class BingSRPCache {
    private final CacheEventListener cacheEventListener;
    private BingAdsRequest lastRequest;
    private boolean loading;
    private List<BingAdPageWrapper> srpCache;
    private long timestamp;

    public interface CacheEventListener {
        void added(boolean z);

        void missed();

        void pageRead();
    }

    public BingSRPCache(@NonNull CacheEventListener cacheEventListener) {
        this.cacheEventListener = cacheEventListener;
    }

    public void clear() {
        this.srpCache = null;
        this.lastRequest = null;
        this.loading = false;
        this.timestamp = -1;
    }

    @Nullable
    public BingAd get(int i) {
        if (this.srpCache == null) {
            return null;
        }
        if (shouldLoadNextPage(i) && !this.loading) {
            this.loading = true;
            this.cacheEventListener.missed();
        }
        BingAd bingAdFromPages = getBingAdFromPages(i);
        return bingAdFromPages == null ? bingAdFromPages : bingAdFromPages;
    }

    private boolean shouldLoadNextPage(int i) {
        return (this.lastRequest == null || this.srpCache == null || getSize() != i) ? false : true;
    }

    public BingAdsRequest nextPageRequest() {
        this.lastRequest.nextPage();
        return this.lastRequest;
    }

    public void setPageRequest(BingAdsRequest bingAdsRequest) {
        this.lastRequest = bingAdsRequest;
    }

    public boolean isEmpty() {
        return this.srpCache == null || this.srpCache.size() == 0;
    }

    public void persist(BingAdPage bingAdPage, long j) {
        this.timestamp = j;
        this.cacheEventListener.added(this.srpCache == null);
        this.loading = false;
        if (this.srpCache != null) {
            this.srpCache.add(new BingAdPageWrapper(bingAdPage));
            return;
        }
        this.srpCache = new LinkedList();
        this.srpCache.add(new BingAdPageWrapper(bingAdPage));
    }

    @Nullable
    public BingAdsImpressionRequest getImpressionRequest() {
        if (this.srpCache == null) {
            return null;
        }
        for (BingAdPageWrapper bingAdPageWrapper : this.srpCache) {
            if (!bingAdPageWrapper.isImpressionsSynched()) {
                List visitedAds = bingAdPageWrapper.getVisitedAds();
                if (visitedAds.size() > 0) {
                    BingAdsImpressionRequest bingAdsImpressionRequest = new BingAdsImpressionRequest();
                    bingAdsImpressionRequest.addAll(visitedAds);
                    bingAdsImpressionRequest.setRguid(bingAdPageWrapper.getRguid());
                    bingAdPageWrapper.setImpressionsSynched(true);
                    return bingAdsImpressionRequest;
                }
            }
        }
        return null;
    }

    private int getSize() {
        if (this.srpCache == null) {
            return 0;
        }
        int i = 0;
        for (BingAdPageWrapper size : this.srpCache) {
            i = size.size() + i;
        }
        return i;
    }

    private BingAd getBingAdFromPages(int i) {
        if (this.srpCache == null) {
            return null;
        }
        for (BingAdPageWrapper bingAdPageWrapper : this.srpCache) {
            if (bingAdPageWrapper.size() > i) {
                BingAd bingAd = bingAdPageWrapper.get(i);
                bingAd.setTrackImpression(true);
                if (bingAdPageWrapper.isAllRead() && !bingAdPageWrapper.isImpressionsSynched()) {
                    this.cacheEventListener.pageRead();
                }
                return bingAd;
            }
            i -= bingAdPageWrapper.size();
        }
        return null;
    }

    public boolean isSameSRP(long j) {
        return this.timestamp == j;
    }
}
