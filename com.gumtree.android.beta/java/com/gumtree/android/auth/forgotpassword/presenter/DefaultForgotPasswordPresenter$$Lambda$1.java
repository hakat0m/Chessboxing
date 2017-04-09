package com.gumtree.android.auth.forgotpassword.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultForgotPasswordPresenter$$Lambda$1 implements Action1 {
    private final DefaultForgotPasswordPresenter arg$1;

    private DefaultForgotPasswordPresenter$$Lambda$1(DefaultForgotPasswordPresenter defaultForgotPasswordPresenter) {
        this.arg$1 = defaultForgotPasswordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultForgotPasswordPresenter defaultForgotPasswordPresenter) {
        return new DefaultForgotPasswordPresenter$$Lambda$1(defaultForgotPasswordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
