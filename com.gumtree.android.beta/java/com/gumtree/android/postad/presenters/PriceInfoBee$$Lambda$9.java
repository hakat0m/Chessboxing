package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftOption;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class PriceInfoBee$$Lambda$9 implements Func1 {
    private static final PriceInfoBee$$Lambda$9 instance = new PriceInfoBee$$Lambda$9();

    private PriceInfoBee$$Lambda$9() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PriceInfoBee.lambda$null$7((DraftOption) obj);
    }
}
