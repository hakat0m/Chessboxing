package com.gumtree.android.postad.promote;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.Comparator;

final /* synthetic */ class PromotionActivity$ViewDelegate$$Lambda$1 implements Comparator {
    private final ViewDelegate arg$1;

    private PromotionActivity$ViewDelegate$$Lambda$1(ViewDelegate viewDelegate) {
        this.arg$1 = viewDelegate;
    }

    public static Comparator lambdaFactory$(ViewDelegate viewDelegate) {
        return new PromotionActivity$ViewDelegate$$Lambda$1(viewDelegate);
    }

    @Hidden
    public int compare(Object obj, Object obj2) {
        return this.arg$1.lambda$showFeatures$0((PromotionFeature) obj, (PromotionFeature) obj2);
    }
}
