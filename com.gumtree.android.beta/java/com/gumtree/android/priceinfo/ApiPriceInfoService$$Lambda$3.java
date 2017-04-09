package com.gumtree.android.priceinfo;

import com.ebay.classifieds.capi.features.FeatureGroups;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiPriceInfoService$$Lambda$3 implements Func1 {
    private static final ApiPriceInfoService$$Lambda$3 instance = new ApiPriceInfoService$$Lambda$3();

    private ApiPriceInfoService$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((FeatureGroups) obj).getFeatureGroup();
    }
}
