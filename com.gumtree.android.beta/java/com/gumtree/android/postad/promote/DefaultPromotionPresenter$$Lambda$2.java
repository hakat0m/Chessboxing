package com.gumtree.android.postad.promote;

import com.gumtree.android.postad.DraftFeature;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$2 implements Func1 {
    private final DefaultPromotionPresenter arg$1;

    private DefaultPromotionPresenter$$Lambda$2(DefaultPromotionPresenter defaultPromotionPresenter) {
        this.arg$1 = defaultPromotionPresenter;
    }

    public static Func1 lambdaFactory$(DefaultPromotionPresenter defaultPromotionPresenter) {
        return new DefaultPromotionPresenter$$Lambda$2(defaultPromotionPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$onLoadFeatures$1((DraftFeature) obj);
    }
}
