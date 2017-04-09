package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.gumtree.android.postad.DraftAd;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundPostAdService implements PostAdService {
    private final PostAdService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundPostAdService(@NonNull PostAdService postAdService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (PostAdService) Validate.notNull(postAdService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<DraftAd> postDraftAd(DraftAd draftAd) {
        return this.decorated.postDraftAd(draftAd).subscribeOn(this.worker).observeOn(this.notifications);
    }

    public Observable<DraftAd> editDraftAd(DraftAd draftAd) {
        return this.decorated.editDraftAd(draftAd).subscribeOn(this.worker).observeOn(this.notifications);
    }

    public Observable<DraftAd> getDraftAd(String str) {
        return this.decorated.getDraftAd(str).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
