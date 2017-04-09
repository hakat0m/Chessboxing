package com.ebay.classifieds.capi.users.forgotpassword;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import retrofit.http.Path;
import rx.Observable;

public interface ForgotPasswordApi {
    @FormUrlEncoded
    @POST("/users/{email}/forgotpassword")
    Observable<Response> forgotPassword(@Path("email") String str, @Field(" ") String str2);
}
