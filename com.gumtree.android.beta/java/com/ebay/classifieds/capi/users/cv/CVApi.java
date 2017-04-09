package com.ebay.classifieds.capi.users.cv;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface CVApi {
    @GET("/users/{username}/storedCvs")
    Observable<CVs> storedCvs(@Path("username") String str);
}
