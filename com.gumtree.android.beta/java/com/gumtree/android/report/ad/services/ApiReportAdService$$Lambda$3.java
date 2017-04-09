package com.gumtree.android.report.ad.services;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class ApiReportAdService$$Lambda$3 implements Func1 {
    private static final ApiReportAdService$$Lambda$3 instance = new ApiReportAdService$$Lambda$3();

    private ApiReportAdService$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiReportAdService.lambda$retrieveReportAdReasons$2((List) obj);
    }
}
