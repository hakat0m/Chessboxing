package com.gumtree.android.srp.suggestion;

import com.gumtree.androidapi.model.KeywordSuggestion;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class CallistoSuggestionProvider$$Lambda$1 implements Func1 {
    private final CallistoSuggestionProvider arg$1;

    private CallistoSuggestionProvider$$Lambda$1(CallistoSuggestionProvider callistoSuggestionProvider) {
        this.arg$1 = callistoSuggestionProvider;
    }

    public static Func1 lambdaFactory$(CallistoSuggestionProvider callistoSuggestionProvider) {
        return new CallistoSuggestionProvider$$Lambda$1(callistoSuggestionProvider);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$suggestFor$0((KeywordSuggestion) obj);
    }
}
