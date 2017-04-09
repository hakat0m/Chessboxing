package com.gumtree.android.srp.suggestion;

import com.gumtree.androidapi.model.KeywordSuggestion;
import rx.Observable;

public interface SuggestionsService {
    Observable<KeywordSuggestion> suggestions(String str, String str2);
}
