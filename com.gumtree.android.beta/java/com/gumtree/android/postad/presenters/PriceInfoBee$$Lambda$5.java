package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$5 implements Func1 {
    private static final PriceInfoBee$$Lambda$5 instance = new PriceInfoBee$$Lambda$5();

    private PriceInfoBee$$Lambda$5() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$fetchPromotionFeatures$4((Throwable) obj);
    }
}
