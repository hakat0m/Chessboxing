package com.ebay.classifieds.capi.features;

import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface FeaturesApi {
    public static final String DEFAULT_PREFIX = "feat";
    public static final String FEATURE_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/feature/v1";

    @POST("/features/ad/{adId}")
    Observable<Void> applyFees(@Path("adId") String str, @Body ApplyFeaturePayload applyFeaturePayload);

    @GET("/features/ad")
    Observable<FeatureGroups> getFeatures(@Query("adId") String str, @Query("categoryId") String str2, @Query("locationId") String str3, @Query("attr[seller_type]") String str4);
}
