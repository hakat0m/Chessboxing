package com.gumtree.android.userprofile.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.users.profile.UserProfile;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundUserProfileService implements UserProfileService {
    private UserProfileService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundUserProfileService(@NonNull UserProfileService userProfileService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (UserProfileService) Validate.notNull(userProfileService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<UserProfile> getLoggedInUserProfile() {
        return this.decorated.getLoggedInUserProfile().subscribeOn(this.worker).observeOn(this.notifications);
    }

    public Observable<UserProfile> getUserProfile(String str) {
        return this.decorated.getUserProfile(str).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
