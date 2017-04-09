package com.gumtree.android.srp.suggestion;

import android.support.annotation.NonNull;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.androidapi.model.KeywordSuggestion;
import org.apache.commons.lang3.Validate;
import rx.Observable;

public class ApiSuggestionsService implements SuggestionsService {
    private final SuggestionsApi suggestionsApi;

    public ApiSuggestionsService(@NonNull SuggestionsApi suggestionsApi) {
        this.suggestionsApi = (SuggestionsApi) Validate.notNull(suggestionsApi);
    }

    public Observable<KeywordSuggestion> suggestions(String str, String str2) {
        return this.suggestionsApi.suggestions(str, str2);
    }
}
