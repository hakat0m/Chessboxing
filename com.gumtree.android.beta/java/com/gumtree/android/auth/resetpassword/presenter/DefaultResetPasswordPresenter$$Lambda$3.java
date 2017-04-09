package com.gumtree.android.auth.resetpassword.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultResetPasswordPresenter$$Lambda$3 implements Action1 {
    private final DefaultResetPasswordPresenter arg$1;

    private DefaultResetPasswordPresenter$$Lambda$3(DefaultResetPasswordPresenter defaultResetPasswordPresenter) {
        this.arg$1 = defaultResetPasswordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultResetPasswordPresenter defaultResetPasswordPresenter) {
        return new DefaultResetPasswordPresenter$$Lambda$3(defaultResetPasswordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$resetPassword$2((Throwable) obj);
    }
}
