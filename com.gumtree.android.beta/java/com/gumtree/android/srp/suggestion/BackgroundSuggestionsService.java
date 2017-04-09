package com.gumtree.android.srp.suggestion;

import android.support.annotation.NonNull;
import com.gumtree.androidapi.model.KeywordSuggestion;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundSuggestionsService implements SuggestionsService {
    private final SuggestionsService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundSuggestionsService(@NonNull SuggestionsService suggestionsService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (SuggestionsService) Validate.notNull(suggestionsService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<KeywordSuggestion> suggestions(String str, String str2) {
        return this.decorated.suggestions(str, str2).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
