package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;
import rx.functions.Func2;

final /* synthetic */ class PriceInfoBee$$Lambda$11 implements Func2 {
    private static final PriceInfoBee$$Lambda$11 instance = new PriceInfoBee$$Lambda$11();

    private PriceInfoBee$$Lambda$11() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return PriceInfoBee.lambda$null$9((Pair) obj, (Pair) obj2);
    }
}
