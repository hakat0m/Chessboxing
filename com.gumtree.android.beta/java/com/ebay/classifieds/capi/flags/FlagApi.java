package com.ebay.classifieds.capi.flags;

import com.ebay.classifieds.capi.flags.ads.FlagAdBody;
import com.ebay.classifieds.capi.flags.ads.FlagAdReasons;
import com.ebay.classifieds.capi.flags.ads.FlagAdResponse;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.PUT;
import retrofit.http.Path;
import rx.Observable;

public interface FlagApi {
    @PUT("/flags/ads/{id}")
    Observable<FlagAdResponse> flagAd(@Path("id") long j, @Body FlagAdBody flagAdBody);

    @GET("/flags/ads/reasons")
    Observable<FlagAdReasons> getFlagAdReasons();
}
