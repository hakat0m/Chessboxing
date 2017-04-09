package com.gumtree.android.ad.treebay.services;

import com.gumtree.android.ad.treebay.TreebayCustomDimensionData;
import com.gumtree.android.api.treebay.ItemSummary;
import com.gumtree.android.api.treebay.TreebayAd;
import java.util.Queue;

public interface TrackingTreebayService {
    void clearTreebayTrackedImpressions();

    void eventTreebayR2SExternalBegin(TreebayAd treebayAd);

    void eventTreebayResultsAdClick(String str, int i);

    void eventTreebayResultsImpressions(ItemSummary[] itemSummaryArr, Queue<Integer> queue);

    void screenViewPVip(String str);

    void setTreebayCustomDimensionData(TreebayCustomDimensionData treebayCustomDimensionData);
}
