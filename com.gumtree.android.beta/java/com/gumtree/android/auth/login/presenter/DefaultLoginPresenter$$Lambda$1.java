package com.gumtree.android.auth.login.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultLoginPresenter$$Lambda$1 implements Action1 {
    private final DefaultLoginPresenter arg$1;

    private DefaultLoginPresenter$$Lambda$1(DefaultLoginPresenter defaultLoginPresenter) {
        this.arg$1 = defaultLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultLoginPresenter defaultLoginPresenter) {
        return new DefaultLoginPresenter$$Lambda$1(defaultLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
