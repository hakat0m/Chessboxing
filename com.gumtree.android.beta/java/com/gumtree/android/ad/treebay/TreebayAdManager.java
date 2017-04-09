package com.gumtree.android.ad.treebay;

import android.support.annotation.NonNull;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.ad.treebay.services.TrackingTreebayService;
import com.gumtree.android.ad.treebay.services.search.TreebaySearchResultService;
import com.gumtree.android.api.treebay.ItemSummary;
import com.gumtree.android.api.treebay.SearchResult;
import com.gumtree.android.api.treebay.ThumbnailImageUrl;
import com.gumtree.android.common.activities.StatefulActivity;
import com.gumtree.android.logging.Timber;
import java.net.URL;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import rx.Subscription;

public class TreebayAdManager {
    public static final int FIRST_TREEBAY_POSITION = 8;
    private static final String ITEM_ID_PLACEHOLDER = "{itemId}";
    public static final int SECOND_TREEBAY_POSITION = 19;
    private Map<Integer, ItemSummary> integerItemSummaryMap;
    private Queue<ItemSummary> itemSummaryQueue;
    private boolean loading;
    private Subscription searchUrlSubscription;
    private TrackingTreebayService trackingTreebayService;
    private String treebayItemTemplateUrl;
    private final TreebaySearchResultService treebaySearchResultService;
    private String treebaySearchUrl;
    private Queue<Integer> untrackedTreebayItemsPositions = new ConcurrentLinkedQueue();

    public TreebayAdManager(@NonNull TreebaySearchResultService treebaySearchResultService, @NonNull TrackingTreebayService trackingTreebayService) {
        this.treebaySearchResultService = (TreebaySearchResultService) Validate.notNull(treebaySearchResultService);
        this.trackingTreebayService = (TrackingTreebayService) Validate.notNull(trackingTreebayService);
        this.integerItemSummaryMap = new HashMap();
    }

    public void resetManager() {
        Timber.d("resetManager", new Object[0]);
        if (!(this.searchUrlSubscription == null || this.searchUrlSubscription.isUnsubscribed())) {
            this.searchUrlSubscription.unsubscribe();
        }
        this.treebaySearchUrl = null;
        this.treebayItemTemplateUrl = null;
        this.itemSummaryQueue = null;
        this.integerItemSummaryMap.clear();
    }

    private void searchItemsError(Throwable th) {
        Timber.d(th, "searchItemsError", new Object[0]);
        this.itemSummaryQueue = null;
    }

    private void searchItemsSuccess(SearchResult searchResult) {
        Timber.d("searchItemsSuccess", new Object[0]);
        this.integerItemSummaryMap.clear();
        this.itemSummaryQueue = new ConcurrentLinkedQueue();
        Collection itemSummaries = searchResult.getItemSummaries();
        if (itemSummaries == null || itemSummaries.size() == 0) {
            Timber.e("Response was valid but no Treebay items exist", new Object[0]);
            return;
        }
        Timber.d("Got %d treebay ads", Integer.valueOf(itemSummaries.size()));
        this.itemSummaryQueue.addAll(itemSummaries);
        this.trackingTreebayService.eventTreebayResultsImpressions(getSummaryQueueList(), getDefaultPositions());
    }

    public void fetchAds(String str, String str2) {
        if (str == null && str2 == null) {
            resetManager();
        }
        if (isSearchUrlUpdated(str)) {
            Timber.d("Fetching ads for %s", str);
            Timber.d("Item template updated to %s", str2);
            this.treebaySearchUrl = str;
            this.treebayItemTemplateUrl = str2;
            this.searchUrlSubscription = this.treebaySearchResultService.getTreebaySearchResults(str).doOnSubscribe(TreebayAdManager$$Lambda$1.lambdaFactory$(this)).doOnNext(TreebayAdManager$$Lambda$4.lambdaFactory$(this)).subscribe(TreebayAdManager$$Lambda$5.lambdaFactory$(this), TreebayAdManager$$Lambda$6.lambdaFactory$(this));
        }
    }

    /* synthetic */ void lambda$fetchAds$0() {
        this.loading = true;
        this.itemSummaryQueue = null;
        this.integerItemSummaryMap.clear();
        this.untrackedTreebayItemsPositions = new ConcurrentLinkedQueue();
    }

    /* synthetic */ void lambda$fetchAds$1(SearchResult searchResult) {
        this.loading = false;
    }

    /* synthetic */ void lambda$fetchAds$2(SearchResult searchResult) {
        searchItemsSuccess(searchResult);
    }

    /* synthetic */ void lambda$fetchAds$3(Throwable th) {
        searchItemsError(th);
    }

    public void populateView(int i, TreebayAdvertItem treebayAdvertItem) {
        ItemSummary itemSummary;
        Timber.d("populateView %d", Integer.valueOf(i));
        if (this.integerItemSummaryMap.containsKey(Integer.valueOf(i))) {
            itemSummary = (ItemSummary) this.integerItemSummaryMap.get(Integer.valueOf(i));
            if (itemSummary == null) {
                itemSummary = getItemSummary();
                this.integerItemSummaryMap.put(Integer.valueOf(i), itemSummary);
            }
        } else {
            itemSummary = getItemSummary();
            this.integerItemSummaryMap.put(Integer.valueOf(i), itemSummary);
        }
        populateView(treebayAdvertItem, itemSummary);
    }

    private ItemSummary getItemSummary() {
        if (this.itemSummaryQueue == null || this.itemSummaryQueue.isEmpty()) {
            return null;
        }
        return (ItemSummary) this.itemSummaryQueue.remove();
    }

    private void populateView(TreebayAdvertItem treebayAdvertItem, ItemSummary itemSummary) {
        if (!this.loading) {
            if (itemSummary == null || this.itemSummaryQueue == null) {
                treebayAdvertItem.hideAd();
                return;
            }
            treebayAdvertItem.setId(itemSummary.getItemId());
            treebayAdvertItem.setTreebayItemUrl(generateItemUrl(itemSummary.getItemId()));
            treebayAdvertItem.showContent();
            treebayAdvertItem.setTitle(itemSummary.getTitle());
            treebayAdvertItem.setDescription(itemSummary.getDescription());
            treebayAdvertItem.setPrice(itemSummary.getPrice() == null ? null : itemSummary.getPrice().getValue());
            treebayAdvertItem.setLocation(itemSummary.getItemLocation());
            List thumbnailImages = itemSummary.getThumbnailImages();
            if (thumbnailImages == null || thumbnailImages.isEmpty()) {
                treebayAdvertItem.loadImage(BuildConfig.FLAVOR);
            } else {
                treebayAdvertItem.loadImage(((ThumbnailImageUrl) thumbnailImages.get(0)).getImageUrl());
            }
        }
    }

    public String generateItemUrl(String str) {
        if (this.treebayItemTemplateUrl != null) {
            return StringUtils.replace(this.treebayItemTemplateUrl, ITEM_ID_PLACEHOLDER, str);
        }
        return null;
    }

    private boolean isSearchUrlUpdated(String str) {
        if (str == null) {
            return false;
        }
        if (this.treebaySearchUrl == null) {
            return true;
        }
        try {
            URL url = new URL(str);
            URL url2 = new URL(this.treebaySearchUrl);
            String searchTerm = getSearchTerm(url);
            String searchTerm2 = getSearchTerm(url2);
            String filterTerm = getFilterTerm(url);
            String filterTerm2 = getFilterTerm(url2);
            if (url.getProtocol().equals(url2.getProtocol()) && url.getHost().equals(url2.getHost()) && url.getPath().equals(url2.getPath()) && searchTerm != null && searchTerm2 != null && searchTerm.equals(searchTerm2) && filterTerm != null && filterTerm2 != null && filterTerm.equals(filterTerm2)) {
                return false;
            }
            return true;
        } catch (Throwable e) {
            Timber.e(e, "Invalid treebay url newUrl: %s, oldUrl: %s", str, this.treebaySearchUrl);
            return false;
        }
    }

    private String getSearchTerm(URL url) {
        return getUrlParameter(url, StatefulActivity.NAME_QUERY);
    }

    private String getFilterTerm(URL url) {
        return getUrlParameter(url, "filter");
    }

    private String getUrlParameter(@NonNull URL url, @NonNull String str) {
        for (String split : url.getQuery().split("&")) {
            String[] split2 = split.split("=");
            if (str.equals(split2[0])) {
                return split2[1];
            }
        }
        return null;
    }

    public ItemSummary[] getSummaryQueueList() {
        if (this.itemSummaryQueue != null) {
            return (ItemSummary[]) this.itemSummaryQueue.toArray(new ItemSummary[this.itemSummaryQueue.size()]);
        }
        return new ItemSummary[0];
    }

    public Queue<Integer> getUntrackedTreebayItemsPositions() {
        return this.untrackedTreebayItemsPositions;
    }

    private ConcurrentLinkedQueue<Integer> getDefaultPositions() {
        ConcurrentLinkedQueue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue();
        concurrentLinkedQueue.add(Integer.valueOf(FIRST_TREEBAY_POSITION));
        concurrentLinkedQueue.add(Integer.valueOf(SECOND_TREEBAY_POSITION));
        return concurrentLinkedQueue;
    }
}
