package com.gumtree.android.report.ad.services;

import com.ebay.classifieds.capi.flags.ads.FlagAdReasons.Reason;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiReportAdService$$Lambda$4 implements Func1 {
    private static final ApiReportAdService$$Lambda$4 instance = new ApiReportAdService$$Lambda$4();

    private ApiReportAdService$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiReportAdService.lambda$retrieveReportAdReasons$3((Reason) obj);
    }
}
