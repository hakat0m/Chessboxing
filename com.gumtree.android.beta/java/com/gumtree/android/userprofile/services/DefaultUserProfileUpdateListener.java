package com.gumtree.android.userprofile.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.userprofile.events.OnUserProfileEvent;
import org.apache.commons.lang3.Validate;

public class DefaultUserProfileUpdateListener implements UserProfileUpdateListener {
    private EventBus eventBus;

    public DefaultUserProfileUpdateListener(@NonNull EventBus eventBus) {
        this.eventBus = (EventBus) Validate.notNull(eventBus);
    }

    public void notifySubscribers(UserProfile userProfile) {
        this.eventBus.post(new OnUserProfileEvent(true, null, userProfile));
    }
}
