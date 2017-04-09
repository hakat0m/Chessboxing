package com.gumtree.android.priceinfo;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class ApiPriceInfoService$$Lambda$2 implements Action1 {
    private final ApiPriceInfoService arg$1;

    private ApiPriceInfoService$$Lambda$2(ApiPriceInfoService apiPriceInfoService) {
        this.arg$1 = apiPriceInfoService;
    }

    public static Action1 lambdaFactory$(ApiPriceInfoService apiPriceInfoService) {
        return new ApiPriceInfoService$$Lambda$2(apiPriceInfoService);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$getFeatureInfo$1((Throwable) obj);
    }
}
