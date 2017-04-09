package com.ebay.classifieds.capi.seo;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface SeoApi {
    public static final String SEO_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/seolookup/v1";

    @GET("/seolookup")
    Observable<Seo> retrieveDetails(@Query("categoryName") String str, @Query("locationName") String str2);
}
