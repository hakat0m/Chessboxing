package com.ebay.classifieds.capi.suggestions;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface SuggestionsApi {
    public static final String SUGGESTION_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/suggestion/v1";

    @GET("/suggestions/locations")
    Observable<LocationSuggestions> getSuggestedLocations(@Query("q") String str);
}
