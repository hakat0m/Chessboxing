package com.gumtree.android.postad.promote;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$6 implements Action1 {
    private final DefaultPromotionPresenter arg$1;

    private DefaultPromotionPresenter$$Lambda$6(DefaultPromotionPresenter defaultPromotionPresenter) {
        this.arg$1 = defaultPromotionPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPromotionPresenter defaultPromotionPresenter) {
        return new DefaultPromotionPresenter$$Lambda$6(defaultPromotionPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onLoadFeatures$5((Throwable) obj);
    }
}
