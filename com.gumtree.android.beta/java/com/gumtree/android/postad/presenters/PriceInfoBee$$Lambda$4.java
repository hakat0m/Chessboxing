package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class PriceInfoBee$$Lambda$4 implements Action1 {
    private final PriceInfoBee arg$1;

    private PriceInfoBee$$Lambda$4(PriceInfoBee priceInfoBee) {
        this.arg$1 = priceInfoBee;
    }

    public static Action1 lambdaFactory$(PriceInfoBee priceInfoBee) {
        return new PriceInfoBee$$Lambda$4(priceInfoBee);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$fetchPromotionFeatures$3((Throwable) obj);
    }
}
