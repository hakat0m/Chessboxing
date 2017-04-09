package com.gumtree.android.vip.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultVipPresenter$$Lambda$1 implements Action1 {
    private final GatedVipView arg$1;

    private DefaultVipPresenter$$Lambda$1(GatedVipView gatedVipView) {
        this.arg$1 = gatedVipView;
    }

    public static Action1 lambdaFactory$(GatedVipView gatedVipView) {
        return new DefaultVipPresenter$$Lambda$1(gatedVipView);
    }

    @Hidden
    public void call(Object obj) {
        DefaultVipPresenter.lambda$new$0(this.arg$1, (Boolean) obj);
    }
}
