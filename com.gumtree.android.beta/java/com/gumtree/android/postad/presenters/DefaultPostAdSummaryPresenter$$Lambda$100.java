package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.promote.PromotionFeature;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$100 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$100 instance = new DefaultPostAdSummaryPresenter$$Lambda$100();

    private DefaultPostAdSummaryPresenter$$Lambda$100() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((PromotionFeature) obj).getFeature().getType();
    }
}
