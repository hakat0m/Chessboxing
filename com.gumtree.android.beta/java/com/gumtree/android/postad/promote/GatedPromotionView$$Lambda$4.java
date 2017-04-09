package com.gumtree.android.postad.promote;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.promote.PromotionPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;

final /* synthetic */ class GatedPromotionView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedPromotionView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPromotionView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.updateSelection((List) obj);
    }
}
