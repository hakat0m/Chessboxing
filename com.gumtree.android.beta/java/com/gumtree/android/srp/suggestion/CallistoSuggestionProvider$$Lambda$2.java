package com.gumtree.android.srp.suggestion;

import com.gumtree.androidapi.model.Suggestion;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Transformer;

final /* synthetic */ class CallistoSuggestionProvider$$Lambda$2 implements Transformer {
    private final CallistoSuggestionProvider arg$1;

    private CallistoSuggestionProvider$$Lambda$2(CallistoSuggestionProvider callistoSuggestionProvider) {
        this.arg$1 = callistoSuggestionProvider;
    }

    public static Transformer lambdaFactory$(CallistoSuggestionProvider callistoSuggestionProvider) {
        return new CallistoSuggestionProvider$$Lambda$2(callistoSuggestionProvider);
    }

    @Hidden
    public Object transform(Object obj) {
        return this.arg$1.lambda$convert$1((Suggestion) obj);
    }
}
