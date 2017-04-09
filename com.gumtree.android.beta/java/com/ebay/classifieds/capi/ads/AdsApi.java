package com.ebay.classifieds.capi.ads;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface AdsApi {
    public static final String DEFAULT_INCLUDED_FIELDS = "title,description,phone,email,modification-date-time,start-date-time,category.localized_name,category.id,locations,link,pictures,id,price.amount,price-frequency,features-active,neighborhood,search-distance,ad-status";
    public static final String NAME_SPACE = "http://www.ebayclassifiedsgroup.com/schema/ad/v1";
    public static final String SORT_DATE_DESCENDING = "DATE_DESCENDING";

    @GET("/ads/{id}")
    Observable<Ad> ad(@Path("id") long j, @Query("_metadata") boolean z);

    @GET("/ads")
    Observable<Ads> getUserAds(@Query("userIds") String str, @Query("page") int i, @Query("size") int i2, @Query("sortType") String str2, @Query("_in") String str3);
}
