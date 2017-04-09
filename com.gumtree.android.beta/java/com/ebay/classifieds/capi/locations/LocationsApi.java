package com.ebay.classifieds.capi.locations;

import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;
import rx.Observable;

public interface LocationsApi {
    public static final String LOCATION_NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/location/v1";

    @GET("/locations/{id}")
    Observable<Location> location(@Path("id") long j, @Query("depth") int i);

    @GET("/locations/postcode")
    Observable<Locations> postcode(@Query("q") String str);
}
