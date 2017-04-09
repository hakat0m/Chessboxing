package com.gumtree.android.priceinfo;

import com.ebay.classifieds.capi.features.Feature;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiPriceInfoService$$Lambda$5 implements Func1 {
    private final ApiPriceInfoService arg$1;

    private ApiPriceInfoService$$Lambda$5(ApiPriceInfoService apiPriceInfoService) {
        this.arg$1 = apiPriceInfoService;
    }

    public static Func1 lambdaFactory$(ApiPriceInfoService apiPriceInfoService) {
        return new ApiPriceInfoService$$Lambda$5(apiPriceInfoService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getFeatureInfo$4((Feature) obj);
    }
}
