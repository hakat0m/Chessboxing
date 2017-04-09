package com.gumtree.android.report.ad.services;

import com.ebay.classifieds.capi.flags.ads.FlagAdResponse;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiReportAdService$$Lambda$1 implements Func1 {
    private static final ApiReportAdService$$Lambda$1 instance = new ApiReportAdService$$Lambda$1();

    private ApiReportAdService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiReportAdService.lambda$reportAd$0((FlagAdResponse) obj);
    }
}
