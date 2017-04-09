package com.gumtree.android.userprofile.services;

import android.support.annotation.NonNull;
import com.gumtree.android.auth.UserLocation;
import com.gumtree.android.userprofile.User;
import rx.Observable;

public interface UserService {
    void setFallbackLocation(@NonNull UserLocation userLocation);

    void update();

    @NonNull
    Observable<User> userUpdates();
}
