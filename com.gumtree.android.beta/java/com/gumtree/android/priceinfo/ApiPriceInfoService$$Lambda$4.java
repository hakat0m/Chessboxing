package com.gumtree.android.priceinfo;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class ApiPriceInfoService$$Lambda$4 implements Func1 {
    private static final ApiPriceInfoService$$Lambda$4 instance = new ApiPriceInfoService$$Lambda$4();

    private ApiPriceInfoService$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from((List) obj);
    }
}
