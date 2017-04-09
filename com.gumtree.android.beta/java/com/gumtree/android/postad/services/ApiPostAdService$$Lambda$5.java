package com.gumtree.android.postad.services;

import com.ebay.classifieds.capi.users.ads.MyAd;
import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiPostAdService$$Lambda$5 implements Func1 {
    private final ApiPostAdService arg$1;
    private final DraftAd arg$2;

    private ApiPostAdService$$Lambda$5(ApiPostAdService apiPostAdService, DraftAd draftAd) {
        this.arg$1 = apiPostAdService;
        this.arg$2 = draftAd;
    }

    public static Func1 lambdaFactory$(ApiPostAdService apiPostAdService, DraftAd draftAd) {
        return new ApiPostAdService$$Lambda$5(apiPostAdService, draftAd);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$editDraftAd$4(this.arg$2, (MyAd) obj);
    }
}
