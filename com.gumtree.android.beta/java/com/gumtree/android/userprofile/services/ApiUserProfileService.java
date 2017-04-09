package com.gumtree.android.userprofile.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.ebay.classifieds.capi.users.profile.UserProfileApi;
import com.gumtree.android.auth.BaseAccountManager;
import rx.Observable;

public class ApiUserProfileService implements UserProfileService {
    private BaseAccountManager accountManager;
    private UserProfileApi api;

    public ApiUserProfileService(UserProfileApi userProfileApi, BaseAccountManager baseAccountManager) {
        this.api = userProfileApi;
        this.accountManager = baseAccountManager;
    }

    public Observable<UserProfile> getLoggedInUserProfile() {
        return this.api.get(this.accountManager.getUsername());
    }

    public Observable<UserProfile> getUserProfile(String str) {
        return this.api.get(str);
    }
}
