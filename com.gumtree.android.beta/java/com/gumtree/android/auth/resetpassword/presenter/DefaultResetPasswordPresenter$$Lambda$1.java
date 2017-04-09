package com.gumtree.android.auth.resetpassword.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultResetPasswordPresenter$$Lambda$1 implements Action1 {
    private final DefaultResetPasswordPresenter arg$1;

    private DefaultResetPasswordPresenter$$Lambda$1(DefaultResetPasswordPresenter defaultResetPasswordPresenter) {
        this.arg$1 = defaultResetPasswordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultResetPasswordPresenter defaultResetPasswordPresenter) {
        return new DefaultResetPasswordPresenter$$Lambda$1(defaultResetPasswordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
