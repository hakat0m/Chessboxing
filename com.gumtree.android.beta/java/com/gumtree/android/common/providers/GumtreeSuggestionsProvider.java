package com.gumtree.android.common.providers;

import android.content.SearchRecentSuggestionsProvider;

public class GumtreeSuggestionsProvider extends SearchRecentSuggestionsProvider {
    public static final String AUTHORITY = "com.gumtree.android.beta.suggestions";
    public static final int MODE = 1;

    public GumtreeSuggestionsProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
