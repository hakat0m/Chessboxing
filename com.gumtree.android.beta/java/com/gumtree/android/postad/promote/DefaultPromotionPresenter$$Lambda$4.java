package com.gumtree.android.postad.promote;

import com.gumtree.android.postad.DraftFeature;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class DefaultPromotionPresenter$$Lambda$4 implements Func1 {
    private final DefaultPromotionPresenter arg$1;
    private final List arg$2;
    private final List arg$3;
    private final int arg$4;

    private DefaultPromotionPresenter$$Lambda$4(DefaultPromotionPresenter defaultPromotionPresenter, List list, List list2, int i) {
        this.arg$1 = defaultPromotionPresenter;
        this.arg$2 = list;
        this.arg$3 = list2;
        this.arg$4 = i;
    }

    public static Func1 lambdaFactory$(DefaultPromotionPresenter defaultPromotionPresenter, List list, List list2, int i) {
        return new DefaultPromotionPresenter$$Lambda$4(defaultPromotionPresenter, list, list2, i);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$onLoadFeatures$3(this.arg$2, this.arg$3, this.arg$4, (DraftFeature) obj);
    }
}
