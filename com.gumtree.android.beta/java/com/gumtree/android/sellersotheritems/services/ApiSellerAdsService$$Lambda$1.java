package com.gumtree.android.sellersotheritems.services;

import com.ebay.classifieds.capi.ads.Ads;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiSellerAdsService$$Lambda$1 implements Func1 {
    private final ApiSellerAdsService arg$1;

    private ApiSellerAdsService$$Lambda$1(ApiSellerAdsService apiSellerAdsService) {
        this.arg$1 = apiSellerAdsService;
    }

    public static Func1 lambdaFactory$(ApiSellerAdsService apiSellerAdsService) {
        return new ApiSellerAdsService$$Lambda$1(apiSellerAdsService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getSellerAdsPage$0((Ads) obj);
    }
}
