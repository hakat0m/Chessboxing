package com.ebay.classifieds.capi.users;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.Header;
import retrofit.http.POST;
import rx.Observable;

public interface UsersApi {
    public static final String NAMESPACE = "http://www.ebayclassifiedsgroup.com/schema/user/v1";
    public static final String PREFIX = "user";
    public static final String REL_GUMTREE = "gumtree";

    @POST("/users/activate")
    Observable<Response> activate(@Body ActivateUser activateUser);

    @POST("/users")
    Observable<Response> register(@Header("X-THREATMETRIX-SESSION-ID") String str, @Body RegisterUser registerUser);
}
