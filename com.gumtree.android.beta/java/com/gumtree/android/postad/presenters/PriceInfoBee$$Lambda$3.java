package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class PriceInfoBee$$Lambda$3 implements Action0 {
    private final PriceInfoBee arg$1;

    private PriceInfoBee$$Lambda$3(PriceInfoBee priceInfoBee) {
        this.arg$1 = priceInfoBee;
    }

    public static Action0 lambdaFactory$(PriceInfoBee priceInfoBee) {
        return new PriceInfoBee$$Lambda$3(priceInfoBee);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$fetchPromotionFeatures$2();
    }
}
