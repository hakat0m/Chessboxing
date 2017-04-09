package com.gumtree.android.metadata.service;

import com.gumtree.android.metadata.model.DraftAdMetadata;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiVRMService$$Lambda$3 implements Func1 {
    private static final ApiVRMService$$Lambda$3 instance = new ApiVRMService$$Lambda$3();

    private ApiVRMService$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiVRMService.lambda$getVRMData$2((DraftAdMetadata) obj);
    }
}
