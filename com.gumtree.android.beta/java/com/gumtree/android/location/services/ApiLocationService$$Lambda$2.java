package com.gumtree.android.location.services;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class ApiLocationService$$Lambda$2 implements Func1 {
    private static final ApiLocationService$$Lambda$2 instance = new ApiLocationService$$Lambda$2();

    private ApiLocationService$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiLocationService.lambda$getSuggestedLocations$1((List) obj);
    }
}
