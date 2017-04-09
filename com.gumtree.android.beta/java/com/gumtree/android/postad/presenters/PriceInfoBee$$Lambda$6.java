package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$6 implements Func1 {
    private static final PriceInfoBee$$Lambda$6 instance = new PriceInfoBee$$Lambda$6();

    private PriceInfoBee$$Lambda$6() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$calculatePriceAndReasons$10((List) obj);
    }
}
