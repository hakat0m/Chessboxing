package com.ebay.classifieds.capi.vrm;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface VRMApi {
    @GET("/vrn/{vrn}")
    Observable<VRNResponses> getVRNAttributes(@Path("vrn") String str, @Query("categoryId") String str2);
}
