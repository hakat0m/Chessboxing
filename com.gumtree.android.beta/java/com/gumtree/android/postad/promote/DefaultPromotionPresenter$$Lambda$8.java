package com.gumtree.android.postad.promote;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$8 implements Predicate {
    private static final DefaultPromotionPresenter$$Lambda$8 instance = new DefaultPromotionPresenter$$Lambda$8();

    private DefaultPromotionPresenter$$Lambda$8() {
    }

    public static Predicate lambdaFactory$() {
        return instance;
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return ((PromotionFeature) obj).isSelected();
    }
}
