package com.gumtree.android.postad.repositories;

import android.support.annotation.NonNull;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.repositories.Repository;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundPostAdRepository implements Repository<DraftAd> {
    private final Repository<DraftAd> decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundPostAdRepository(@NonNull Repository<DraftAd> repository, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (Repository) Validate.notNull(repository);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<Void> save(DraftAd draftAd) {
        return this.decorated.save(draftAd).subscribeOn(this.worker).observeOn(this.notifications);
    }

    public Observable<DraftAd> load(String str) {
        return this.decorated.load(str).subscribeOn(this.worker).observeOn(this.notifications);
    }

    public Observable<Void> clear(String str) {
        return this.decorated.clear(str).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
