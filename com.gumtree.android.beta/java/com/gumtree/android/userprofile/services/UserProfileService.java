package com.gumtree.android.userprofile.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import rx.Observable;

public interface UserProfileService {
    Observable<UserProfile> getLoggedInUserProfile();

    Observable<UserProfile> getUserProfile(String str);
}
