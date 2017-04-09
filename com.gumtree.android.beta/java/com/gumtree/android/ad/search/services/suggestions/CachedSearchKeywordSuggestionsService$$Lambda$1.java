package com.gumtree.android.ad.search.services.suggestions;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class CachedSearchKeywordSuggestionsService$$Lambda$1 implements Action1 {
    private final CachedSearchKeywordSuggestionsService arg$1;
    private final String arg$2;
    private final String arg$3;

    private CachedSearchKeywordSuggestionsService$$Lambda$1(CachedSearchKeywordSuggestionsService cachedSearchKeywordSuggestionsService, String str, String str2) {
        this.arg$1 = cachedSearchKeywordSuggestionsService;
        this.arg$2 = str;
        this.arg$3 = str2;
    }

    public static Action1 lambdaFactory$(CachedSearchKeywordSuggestionsService cachedSearchKeywordSuggestionsService, String str, String str2) {
        return new CachedSearchKeywordSuggestionsService$$Lambda$1(cachedSearchKeywordSuggestionsService, str, str2);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$suggestions$0(this.arg$2, this.arg$3, (Throwable) obj);
    }
}
