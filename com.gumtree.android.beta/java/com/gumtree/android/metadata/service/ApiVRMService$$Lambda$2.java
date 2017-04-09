package com.gumtree.android.metadata.service;

import com.ebay.classifieds.capi.metadata.Metadata;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiVRMService$$Lambda$2 implements Func1 {
    private final ApiVRMService arg$1;
    private final String arg$2;

    private ApiVRMService$$Lambda$2(ApiVRMService apiVRMService, String str) {
        this.arg$1 = apiVRMService;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(ApiVRMService apiVRMService, String str) {
        return new ApiVRMService$$Lambda$2(apiVRMService, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getVRMData$1(this.arg$2, (Metadata) obj);
    }
}
