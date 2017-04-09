package com.gumtree.android.ad.treebay.services.item;

import android.support.annotation.NonNull;
import com.gumtree.android.api.treebay.ItemSummary;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundTreebayAdDetailsService implements TreebayAdDetailsService {
    private TreebayAdDetailsService decorated;
    private Scheduler notifications;
    private Scheduler worker;

    public BackgroundTreebayAdDetailsService(@NonNull TreebayAdDetailsService treebayAdDetailsService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (TreebayAdDetailsService) Validate.notNull(treebayAdDetailsService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<ItemSummary> getTreebayAdDetails(String str) {
        return this.decorated.getTreebayAdDetails(str).subscribeOn(this.worker).observeOn(this.notifications);
    }

    public void invalidateCache() {
        this.decorated.invalidateCache();
    }
}
