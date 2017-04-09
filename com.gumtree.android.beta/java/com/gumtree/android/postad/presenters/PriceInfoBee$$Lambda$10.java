package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftOption;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$10 implements Func1 {
    private static final PriceInfoBee$$Lambda$10 instance = new PriceInfoBee$$Lambda$10();

    private PriceInfoBee$$Lambda$10() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$null$8((DraftOption) obj);
    }
}
