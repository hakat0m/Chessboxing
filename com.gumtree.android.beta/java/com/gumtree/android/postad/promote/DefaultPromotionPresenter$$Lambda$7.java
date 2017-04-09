package com.gumtree.android.postad.promote;

import com.gumtree.android.postad.DraftFeature;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.collections4.Predicate;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$7 implements Predicate {
    private final DraftFeature arg$1;

    private DefaultPromotionPresenter$$Lambda$7(DraftFeature draftFeature) {
        this.arg$1 = draftFeature;
    }

    public static Predicate lambdaFactory$(DraftFeature draftFeature) {
        return new DefaultPromotionPresenter$$Lambda$7(draftFeature);
    }

    @Hidden
    public boolean evaluate(Object obj) {
        return this.arg$1.equals(((PromotionFeature) obj).getFeature());
    }
}
