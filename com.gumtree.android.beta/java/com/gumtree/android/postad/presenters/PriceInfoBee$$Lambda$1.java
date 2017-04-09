package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftFeature;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$1 implements Func1 {
    private final List arg$1;

    private PriceInfoBee$$Lambda$1(List list) {
        this.arg$1 = list;
    }

    public static Func1 lambdaFactory$(List list) {
        return new PriceInfoBee$$Lambda$1(list);
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$fetchPromotionFeatures$0(this.arg$1, (DraftFeature) obj);
    }
}
