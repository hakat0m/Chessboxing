package com.ebay.classifieds.capi.users.watchlist;

import com.ebay.classifieds.capi.ads.Ads;
import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface WatchListApi {
    @PUT("/users/{username}/watchlist/{id}")
    Observable<Ads> add(@Path("username") String str, @Path("id") String str2, @Body String str3);

    @PUT("/users/{username}/watchlist/{id}")
    void add(@Path("username") String str, @Path("id") String str2, @Body String str3, Callback<Ads> callback);

    @GET("/users/{username}/watchlist")
    Observable<Ads> getAll(@Path("username") String str);

    @GET("/users/{username}/watchlist")
    void getAll(@Path("username") String str, Callback<Ads> callback);

    @DELETE("/users/{username}/watchlist/{id}")
    Observable<Ads> remove(@Path("username") String str, @Path("id") String str2);

    @DELETE("/users/{username}/watchlist/{id}")
    void remove(@Path("username") String str, @Path("id") String str2, Callback<Ads> callback);
}
