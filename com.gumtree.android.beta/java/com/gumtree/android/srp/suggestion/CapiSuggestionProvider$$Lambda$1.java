package com.gumtree.android.srp.suggestion;

import com.gumtree.android.suggestions.Suggestion;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Transformer;

final /* synthetic */ class CapiSuggestionProvider$$Lambda$1 implements Transformer {
    private final CapiSuggestionProvider arg$1;

    private CapiSuggestionProvider$$Lambda$1(CapiSuggestionProvider capiSuggestionProvider) {
        this.arg$1 = capiSuggestionProvider;
    }

    public static Transformer lambdaFactory$(CapiSuggestionProvider capiSuggestionProvider) {
        return new CapiSuggestionProvider$$Lambda$1(capiSuggestionProvider);
    }

    @Hidden
    public Object transform(Object obj) {
        return this.arg$1.convert((Suggestion) obj);
    }
}
