package com.gumtree.android.auth.forgotpassword.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultForgotPasswordPresenter$$Lambda$2 implements Action1 {
    private final DefaultForgotPasswordPresenter arg$1;

    private DefaultForgotPasswordPresenter$$Lambda$2(DefaultForgotPasswordPresenter defaultForgotPasswordPresenter) {
        this.arg$1 = defaultForgotPasswordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultForgotPasswordPresenter defaultForgotPasswordPresenter) {
        return new DefaultForgotPasswordPresenter$$Lambda$2(defaultForgotPasswordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$forgotPassword$1((String) obj);
    }
}
