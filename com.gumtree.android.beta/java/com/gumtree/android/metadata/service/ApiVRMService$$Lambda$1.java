package com.gumtree.android.metadata.service;

import com.ebay.classifieds.capi.vrm.VRNResponses;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiVRMService$$Lambda$1 implements Func1 {
    private final ApiVRMService arg$1;

    private ApiVRMService$$Lambda$1(ApiVRMService apiVRMService) {
        this.arg$1 = apiVRMService;
    }

    public static Func1 lambdaFactory$(ApiVRMService apiVRMService) {
        return new ApiVRMService$$Lambda$1(apiVRMService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getVRMData$0((VRNResponses) obj);
    }
}
