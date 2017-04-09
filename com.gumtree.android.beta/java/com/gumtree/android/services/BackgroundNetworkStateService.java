package com.gumtree.android.services;

import android.support.annotation.NonNull;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundNetworkStateService implements NetworkStateService {
    private final NetworkStateService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundNetworkStateService(@NonNull NetworkStateService networkStateService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (NetworkStateService) Validate.notNull(networkStateService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<NetworkState> getNetworkState() {
        return this.decorated.getNetworkState().subscribeOn(this.worker).observeOn(this.notifications);
    }
}
