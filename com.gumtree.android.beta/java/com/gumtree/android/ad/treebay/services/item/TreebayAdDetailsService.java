package com.gumtree.android.ad.treebay.services.item;

import com.gumtree.android.api.treebay.ItemSummary;
import rx.Observable;

public interface TreebayAdDetailsService {
    Observable<ItemSummary> getTreebayAdDetails(String str);

    void invalidateCache();
}
