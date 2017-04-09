package com.gumtree.android.vip.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultVipPresenter$$Lambda$4 implements Action0 {
    private final DefaultVipPresenter arg$1;

    private DefaultVipPresenter$$Lambda$4(DefaultVipPresenter defaultVipPresenter) {
        this.arg$1 = defaultVipPresenter;
    }

    public static Action0 lambdaFactory$(DefaultVipPresenter defaultVipPresenter) {
        return new DefaultVipPresenter$$Lambda$4(defaultVipPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$onCallSelected$3();
    }
}
