package com.gumtree.android.ad.treebay.services.search;

import com.gumtree.android.api.treebay.SearchResult;
import rx.Observable;

public interface TreebaySearchResultService {
    Observable<SearchResult> getTreebaySearchResults(String str);
}
