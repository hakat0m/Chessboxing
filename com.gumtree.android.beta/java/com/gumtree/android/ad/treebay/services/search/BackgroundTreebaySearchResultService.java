package com.gumtree.android.ad.treebay.services.search;

import android.support.annotation.NonNull;
import com.gumtree.android.api.treebay.SearchResult;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundTreebaySearchResultService implements TreebaySearchResultService {
    private final TreebaySearchResultService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundTreebaySearchResultService(@NonNull TreebaySearchResultService treebaySearchResultService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (TreebaySearchResultService) Validate.notNull(treebaySearchResultService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<SearchResult> getTreebaySearchResults(String str) {
        return this.decorated.getTreebaySearchResults(str).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
