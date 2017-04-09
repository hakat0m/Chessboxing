package com.gumtree.android.auth.google.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultGoogleLoginPresenter$$Lambda$3 implements Action0 {
    private final DefaultGoogleLoginPresenter arg$1;

    private DefaultGoogleLoginPresenter$$Lambda$3(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        this.arg$1 = defaultGoogleLoginPresenter;
    }

    public static Action0 lambdaFactory$(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        return new DefaultGoogleLoginPresenter$$Lambda$3(defaultGoogleLoginPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$startGoogleLogin$2();
    }
}
