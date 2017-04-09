package com.gumtree.android.userprofile.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.users.profile.UserAddress;
import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.Log;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.auth.UserLocation;
import com.gumtree.android.userprofile.User;
import com.gumtree.android.userprofile.UserProfileStatusService;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.subjects.BehaviorSubject;

public class DefaultUserService implements UserService {
    private final BaseAccountManager accountManager;
    private final UserProfileStatusService profileStatusService;
    private final UserProfileService userProfileService;
    private final UserProfileUpdateListener userProfileUpdateListener;
    private BehaviorSubject<User> userProfiles = BehaviorSubject.create();

    public DefaultUserService(@NonNull UserProfileService userProfileService, @NonNull BaseAccountManager baseAccountManager, @NonNull UserProfileStatusService userProfileStatusService, @NonNull UserProfileUpdateListener userProfileUpdateListener) {
        this.userProfileService = (UserProfileService) Validate.notNull(userProfileService);
        this.accountManager = (BaseAccountManager) Validate.notNull(baseAccountManager);
        this.profileStatusService = (UserProfileStatusService) Validate.notNull(userProfileStatusService);
        this.userProfileUpdateListener = (UserProfileUpdateListener) Validate.notNull(userProfileUpdateListener);
    }

    @NonNull
    public Observable<User> userUpdates() {
        if (this.profileStatusService.isProfileDirty()) {
            update();
        }
        return this.userProfiles.filter(DefaultUserService$$Lambda$1.lambdaFactory$());
    }

    static /* synthetic */ Boolean lambda$userUpdates$0(User user) {
        return Boolean.valueOf(user != null);
    }

    public void update() {
        this.userProfileService.getLoggedInUserProfile().doOnSubscribe(DefaultUserService$$Lambda$4.lambdaFactory$(this)).doOnNext(DefaultUserService$$Lambda$5.lambdaFactory$(this)).doOnNext(DefaultUserService$$Lambda$6.lambdaFactory$(this)).map(DefaultUserService$$Lambda$7.lambdaFactory$()).onErrorResumeNext(DefaultUserService$$Lambda$8.lambdaFactory$(this)).subscribe(DefaultUserService$$Lambda$9.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$update$1() {
        this.userProfiles.onNext(null);
    }

    /* synthetic */ void lambda$update$2(UserProfile userProfile) {
        setUserProfile(userProfile);
    }

    /* synthetic */ void lambda$update$3(UserProfile userProfile) {
        this.userProfileUpdateListener.notifySubscribers(userProfile);
    }

    /* synthetic */ Observable lambda$update$5(Throwable th) {
        Log.e(getClass().getSimpleName(), "Error when retrieving user profile. Using local details.", th);
        return Observable.just(Boolean.valueOf(true));
    }

    /* synthetic */ void lambda$update$6(Boolean bool) {
        this.profileStatusService.setProfileDirty(bool.booleanValue());
        this.userProfiles.onNext(getUser());
    }

    @NonNull
    private User getUser() {
        return User.builder().userEmail(this.accountManager.getUsername()).contactEmail(this.accountManager.getContactEmail()).contactPhone(this.accountManager.getContactPhone()).userAddress(this.accountManager.getUserLocation()).userId(this.accountManager.getUserId()).build();
    }

    private void setUserProfile(UserProfile userProfile) {
        this.accountManager.setContactEmail(userProfile.getPrimaryContactEmail());
        this.accountManager.setContactPhone(userProfile.getPhoneNumber());
        this.accountManager.setDisplayName(userProfile.getDisplayName());
        this.accountManager.setHashedEmail(userProfile.getHashedUserEmailHex());
        UserAddress userAddress = userProfile.getUserAddress();
        this.accountManager.setUserLocation(userAddress != null ? convert(userAddress) : null);
    }

    public void setFallbackLocation(@NonNull UserLocation userLocation) {
        this.accountManager.setUserLocation(userLocation);
        this.userProfiles.onNext(getUser());
        this.profileStatusService.setProfileDirty(true);
    }

    @NonNull
    private UserLocation convert(@NonNull UserAddress userAddress) {
        return new UserLocation(userAddress.getLocationId(), userAddress.getCity(), userAddress.getNeighborhood(), userAddress.getVisibleOnMap(), userAddress.getZipCode());
    }
}
