package com.gumtree.android.location.services;

import com.ebay.classifieds.capi.suggestions.LocationSuggestion;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLocationService$$Lambda$3 implements Func1 {
    private final ApiLocationService arg$1;

    private ApiLocationService$$Lambda$3(ApiLocationService apiLocationService) {
        this.arg$1 = apiLocationService;
    }

    public static Func1 lambdaFactory$(ApiLocationService apiLocationService) {
        return new ApiLocationService$$Lambda$3(apiLocationService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getSuggestedLocations$2((LocationSuggestion) obj);
    }
}
