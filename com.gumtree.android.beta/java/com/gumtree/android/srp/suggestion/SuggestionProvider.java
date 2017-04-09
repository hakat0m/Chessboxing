package com.gumtree.android.srp.suggestion;

import android.support.annotation.Nullable;

public interface SuggestionProvider {
    @Nullable
    AutoCompleteSuggestions suggestFor(String str, String str2);
}
