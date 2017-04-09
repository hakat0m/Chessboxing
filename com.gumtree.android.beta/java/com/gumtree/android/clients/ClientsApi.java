package com.gumtree.android.clients;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

public interface ClientsApi {
    @GET("/clients/latestversion")
    Observable<LatestVersion> latestVersion(@Query("appId") String str, @Query("version") int i);
}
