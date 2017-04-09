package com.ebay.classifieds.capi.users.login;

import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.POST;
import rx.Observable;

public interface LoginApi {
    public static final String REL_FACEBOOK = "facebook";
    public static final String REL_GOOGLE = "google";
    public static final String REL_GOOGLE_ID = "googleid";

    @FormUrlEncoded
    @POST("/users/login")
    Observable<UserLogins> login(@Field("username") String str, @Field("password") String str2);

    @FormUrlEncoded
    @POST("/users/login")
    Observable<UserLogins> login(@Field("username") String str, @Field("password") String str2, @Field("rel") String str3);
}
