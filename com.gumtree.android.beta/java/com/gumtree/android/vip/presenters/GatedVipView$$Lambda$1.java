package com.gumtree.android.vip.presenters;

import com.gumtree.android.vip.presenters.VipPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedVipView$$Lambda$1 implements Action1 {
    private final GatedVipView arg$1;

    private GatedVipView$$Lambda$1(GatedVipView gatedVipView) {
        this.arg$1 = gatedVipView;
    }

    public static Action1 lambdaFactory$(GatedVipView gatedVipView) {
        return new GatedVipView$$Lambda$1(gatedVipView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
