package com.gumtree.android.userprofile.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;

public interface UserProfileUpdateListener {
    void notifySubscribers(UserProfile userProfile);
}
