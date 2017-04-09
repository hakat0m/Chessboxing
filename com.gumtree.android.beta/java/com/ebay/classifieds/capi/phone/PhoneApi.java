package com.ebay.classifieds.capi.phone;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface PhoneApi {
    @GET("/ads/phone/{adId}")
    Observable<RevealPhoneNumber> getFreespeeNumber(@Path("adId") long j);
}
