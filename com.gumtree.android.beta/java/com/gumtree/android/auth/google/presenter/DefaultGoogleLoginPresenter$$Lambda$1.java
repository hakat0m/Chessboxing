package com.gumtree.android.auth.google.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultGoogleLoginPresenter$$Lambda$1 implements Action1 {
    private final DefaultGoogleLoginPresenter arg$1;

    private DefaultGoogleLoginPresenter$$Lambda$1(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        this.arg$1 = defaultGoogleLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultGoogleLoginPresenter defaultGoogleLoginPresenter) {
        return new DefaultGoogleLoginPresenter$$Lambda$1(defaultGoogleLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
