package com.gumtree.android.postad.promote;

import com.gumtree.android.postad.DraftFeature;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$3 implements Func1 {
    private final DefaultPromotionPresenter arg$1;
    private final String arg$2;

    private DefaultPromotionPresenter$$Lambda$3(DefaultPromotionPresenter defaultPromotionPresenter, String str) {
        this.arg$1 = defaultPromotionPresenter;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(DefaultPromotionPresenter defaultPromotionPresenter, String str) {
        return new DefaultPromotionPresenter$$Lambda$3(defaultPromotionPresenter, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$onLoadFeatures$2(this.arg$2, (DraftFeature) obj);
    }
}
