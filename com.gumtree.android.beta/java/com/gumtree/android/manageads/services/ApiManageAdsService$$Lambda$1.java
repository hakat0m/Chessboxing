package com.gumtree.android.manageads.services;

import com.ebay.classifieds.capi.users.ads.MyAds;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiManageAdsService$$Lambda$1 implements Func1 {
    private final ApiManageAdsService arg$1;

    private ApiManageAdsService$$Lambda$1(ApiManageAdsService apiManageAdsService) {
        this.arg$1 = apiManageAdsService;
    }

    public static Func1 lambdaFactory$(ApiManageAdsService apiManageAdsService) {
        return new ApiManageAdsService$$Lambda$1(apiManageAdsService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getAds$0((MyAds) obj);
    }
}
