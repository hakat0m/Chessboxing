package com.ebay.classifieds.capi.users.profile;

import retrofit.http.GET;
import retrofit.http.Path;
import rx.Observable;

public interface UserProfileApi {
    public static final String SCHEMA_USER_PROFILE = "http://www.ebayclassifiedsgroup.com/schema/user/v1";

    @GET("/users/{username}/profile")
    Observable<UserProfile> get(@Path("username") String str);
}
