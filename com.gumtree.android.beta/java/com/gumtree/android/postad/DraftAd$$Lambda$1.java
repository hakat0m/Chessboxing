package com.gumtree.android.postad;

import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class DraftAd$$Lambda$1 implements Predicate {
    private final Type arg$1;

    private DraftAd$$Lambda$1(Type type) {
        this.arg$1 = type;
    }

    public static Predicate lambdaFactory$(Type type) {
        return new DraftAd$$Lambda$1(type);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return DraftAd.lambda$containsSelectedFeature$0(this.arg$1, (PromotionFeature) obj);
    }
}
