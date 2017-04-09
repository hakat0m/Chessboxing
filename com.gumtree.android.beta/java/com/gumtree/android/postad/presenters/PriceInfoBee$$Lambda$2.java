package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$2 implements Func1 {
    private static final PriceInfoBee$$Lambda$2 instance = new PriceInfoBee$$Lambda$2();

    private PriceInfoBee$$Lambda$2() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$fetchPromotionFeatures$1((PromotionFeature) obj);
    }
}
