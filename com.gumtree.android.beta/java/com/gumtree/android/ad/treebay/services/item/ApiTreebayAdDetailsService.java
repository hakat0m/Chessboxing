package com.gumtree.android.ad.treebay.services.item;

import android.support.annotation.NonNull;
import com.gumtree.android.api.treebay.ItemSummary;
import com.gumtree.android.api.treebay.TreebayApi;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class ApiTreebayAdDetailsService implements TreebayAdDetailsService {
    private Map<String, ItemSummary> map = new HashMap();
    private final TreebayApi treebayApi;

    public ApiTreebayAdDetailsService(@NonNull TreebayApi treebayApi) {
        this.treebayApi = (TreebayApi) Validate.notNull(treebayApi);
    }

    public Observable<ItemSummary> getTreebayAdDetails(String str) {
        ItemSummary itemSummary = (ItemSummary) this.map.get(str);
        if (itemSummary != null) {
            return Observable.just(itemSummary);
        }
        return this.treebayApi.getTreebayItemData(str).map(ApiTreebayAdDetailsService$$Lambda$1.lambdaFactory$(this, str));
    }

    /* synthetic */ ItemSummary lambda$getTreebayAdDetails$0(String str, ItemSummary itemSummary) {
        this.map.put(str, itemSummary);
        return itemSummary;
    }

    public void invalidateCache() {
        this.map.clear();
    }
}
