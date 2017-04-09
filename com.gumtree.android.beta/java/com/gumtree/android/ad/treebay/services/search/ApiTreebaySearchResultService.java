package com.gumtree.android.ad.treebay.services.search;

import android.support.annotation.NonNull;
import com.gumtree.android.api.treebay.SearchResult;
import com.gumtree.android.api.treebay.TreebayApi;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class ApiTreebaySearchResultService implements TreebaySearchResultService {
    private final TreebayApi treebayApi;

    public ApiTreebaySearchResultService(@NonNull TreebayApi treebayApi) {
        this.treebayApi = (TreebayApi) Validate.notNull(treebayApi);
    }

    public Observable<SearchResult> getTreebaySearchResults(String str) {
        return this.treebayApi.getTreebaySearchResults(str);
    }
}
