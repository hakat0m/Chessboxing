package com.gumtree.android.postad.promote;

import com.gumtree.android.postad.promote.PromotionPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPromotionView$$Lambda$1 implements Action1 {
    private final GatedPromotionView arg$1;

    private GatedPromotionView$$Lambda$1(GatedPromotionView gatedPromotionView) {
        this.arg$1 = gatedPromotionView;
    }

    public static Action1 lambdaFactory$(GatedPromotionView gatedPromotionView) {
        return new GatedPromotionView$$Lambda$1(gatedPromotionView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
