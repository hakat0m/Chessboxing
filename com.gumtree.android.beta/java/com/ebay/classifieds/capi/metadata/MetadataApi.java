package com.ebay.classifieds.capi.metadata;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface MetadataApi {
    @GET("/ads/metadata/{categoryId}")
    Observable<Metadata> getMetadata(@Path("categoryId") String str);
}
