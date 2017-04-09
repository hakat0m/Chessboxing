package com.gumtree.android.auth.google.presenter;

import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultGoogleLoginPresenter$$Lambda$4 implements Action1 {
    private final DefaultGoogleLoginPresenter arg$1;

    private DefaultGoogleLoginPresenter$$Lambda$4(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        this.arg$1 = defaultGoogleLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        return new DefaultGoogleLoginPresenter$$Lambda$4(defaultGoogleLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$startGoogleLogin$3((AuthResult) obj);
    }
}
