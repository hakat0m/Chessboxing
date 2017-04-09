package com.gumtree.android.ad.search.services.suggestions;

import com.gumtree.androidapi.model.KeywordSuggestion;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiSearchKeywordSuggestionsService$$Lambda$1 implements Func1 {
    private final ApiSearchKeywordSuggestionsService arg$1;

    private ApiSearchKeywordSuggestionsService$$Lambda$1(ApiSearchKeywordSuggestionsService apiSearchKeywordSuggestionsService) {
        this.arg$1 = apiSearchKeywordSuggestionsService;
    }

    public static Func1 lambdaFactory$(ApiSearchKeywordSuggestionsService apiSearchKeywordSuggestionsService) {
        return new ApiSearchKeywordSuggestionsService$$Lambda$1(apiSearchKeywordSuggestionsService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$suggestions$1((KeywordSuggestion) obj);
    }
}
