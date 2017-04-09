package com.gumtree.android.vip.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultVipPresenter$$Lambda$3 implements Action0 {
    private final DefaultVipPresenter arg$1;

    private DefaultVipPresenter$$Lambda$3(DefaultVipPresenter defaultVipPresenter) {
        this.arg$1 = defaultVipPresenter;
    }

    public static Action0 lambdaFactory$(DefaultVipPresenter defaultVipPresenter) {
        return new DefaultVipPresenter$$Lambda$3(defaultVipPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$onCallSelected$2();
    }
}
