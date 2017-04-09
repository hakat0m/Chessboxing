package com.gumtree.android.userprofile.events;

import com.ebay.classifieds.capi.users.profile.UserProfile;

@Deprecated
public class OnUserProfileEvent {
    private Exception exception;
    private boolean success;
    private UserProfile userProfile;

    public OnUserProfileEvent(boolean z, Exception exception, UserProfile userProfile) {
        this.success = z;
        this.exception = exception;
        this.userProfile = userProfile;
    }

    public boolean isSuccess() {
        return this.success;
    }

    public Exception getException() {
        return this.exception;
    }

    public UserProfile getUserProfile() {
        return this.userProfile;
    }
}
