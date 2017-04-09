package com.gumtree.android.location.services;

import com.ebay.classifieds.capi.suggestions.LocationSuggestions;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLocationService$$Lambda$1 implements Func1 {
    private static final ApiLocationService$$Lambda$1 instance = new ApiLocationService$$Lambda$1();

    private ApiLocationService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiLocationService.lambda$getSuggestedLocations$0((LocationSuggestions) obj);
    }
}
