package com.gumtree.android.report.ad.services;

import com.ebay.classifieds.capi.flags.ads.FlagAdReasons;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiReportAdService$$Lambda$2 implements Func1 {
    private static final ApiReportAdService$$Lambda$2 instance = new ApiReportAdService$$Lambda$2();

    private ApiReportAdService$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiReportAdService.lambda$retrieveReportAdReasons$1((FlagAdReasons) obj);
    }
}
