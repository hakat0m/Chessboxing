package com.gumtree.android.api.callisto.suggest;

import com.gumtree.androidapi.model.KeywordSuggestion;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface SuggestionsApi {
    @GET("suggest/ads")
    Observable<KeywordSuggestion> suggestions(@Query("q") String str, @Query("categoryId") String str2);
}
