package com.gumtree.android.vip.services;

import com.ebay.classifieds.capi.phone.RevealPhoneNumber;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiVipService$$Lambda$1 implements Func1 {
    private static final ApiVipService$$Lambda$1 instance = new ApiVipService$$Lambda$1();

    private ApiVipService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiVipService.lambda$getFreespeeNumber$0((RevealPhoneNumber) obj);
    }
}
