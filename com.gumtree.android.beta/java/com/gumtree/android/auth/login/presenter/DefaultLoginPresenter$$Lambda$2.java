package com.gumtree.android.auth.login.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultLoginPresenter$$Lambda$2 implements Action0 {
    private final DefaultLoginPresenter arg$1;

    private DefaultLoginPresenter$$Lambda$2(DefaultLoginPresenter defaultLoginPresenter) {
        this.arg$1 = defaultLoginPresenter;
    }

    public static Action0 lambdaFactory$(DefaultLoginPresenter defaultLoginPresenter) {
        return new DefaultLoginPresenter$$Lambda$2(defaultLoginPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$login$1();
    }
}
