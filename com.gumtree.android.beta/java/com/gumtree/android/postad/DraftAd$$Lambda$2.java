package com.gumtree.android.postad;

import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class DraftAd$$Lambda$2 implements Predicate {
    private static final DraftAd$$Lambda$2 instance = new DraftAd$$Lambda$2();

    private DraftAd$$Lambda$2() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return Type.INSERTION.equals(((PromotionFeature) obj).getFeature().getType());
    }
}
