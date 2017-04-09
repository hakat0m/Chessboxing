package com.gumtree.android.postad.services;

import com.ebay.classifieds.capi.users.ads.MyAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiPostAdService$$Lambda$3 implements Func1 {
    private final ApiPostAdService arg$1;

    private ApiPostAdService$$Lambda$3(ApiPostAdService apiPostAdService) {
        this.arg$1 = apiPostAdService;
    }

    public static Func1 lambdaFactory$(ApiPostAdService apiPostAdService) {
        return new ApiPostAdService$$Lambda$3(apiPostAdService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$postDraftAd$2((MyAd) obj);
    }
}
