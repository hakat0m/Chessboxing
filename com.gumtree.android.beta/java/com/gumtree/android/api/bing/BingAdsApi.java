package com.gumtree.android.api.bing;

import java.util.Map;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.http.QueryMap;

public interface BingAdsApi {
    @POST("/partner/bingimpression")
    BingAdsImpressionResponse impressions(@Query("rguid") String str, @Query("appid") String str2, @Body BingAdsImpressionRequest bingAdsImpressionRequest);

    @GET("/partner/bing")
    BingAdPage search(@Header("Referer") String str, @QueryMap Map<String, String> map);
}
