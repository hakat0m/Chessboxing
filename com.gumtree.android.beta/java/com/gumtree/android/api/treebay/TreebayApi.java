package com.gumtree.android.api.treebay;

import retrofit2.http.GET;
import retrofit2.http.Url;
import rx.Observable;

public interface TreebayApi {
    @GET
    Observable<ItemSummary> getTreebayItemData(@Url String str);

    @GET
    Observable<SearchResult> getTreebaySearchResults(@Url String str);
}
