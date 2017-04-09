package com.ebay.classifieds.capi.users.ads;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface UserAdsApi {
    public static final String SCHEMA_AD = "http://www.ebayclassifiedsgroup.com/schema/ad/v1";

    @DELETE("/users/{username}/ads/{id}")
    Observable<Response> delete(@Path("username") String str, @Path("id") String str2);

    @PUT("/users/{username}/ads/{id}")
    Observable<MyAd> edit(@Path("username") String str, @Body MyAd myAd, @Path("id") String str2);

    @GET("/users/{username}/ads/{id}")
    Observable<MyAd> getAd(@Path("username") String str, @Path("id") String str2);

    @GET("/users/{username}/ads")
    Observable<MyAds> getAds(@Path("username") String str, @Query("page") int i, @Query("size") int i2, @Query("statuses") String str2, @Query("includeTopAds") String str3);

    @POST("/users/{username}/ads")
    Observable<MyAd> post(@Path("username") String str, @Body MyAd myAd);
}
