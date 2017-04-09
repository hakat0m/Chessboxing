package com.ebay.classifieds.capi.users.resetpassword;

import retrofit.client.Response;
import retrofit.http.Body;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface ResetPasswordApi {
    @POST("/users/{email}/resetpassword")
    Observable<Response> resetPassword(@Path("email") String str, @Body ResetPasswordBody resetPasswordBody);
}
