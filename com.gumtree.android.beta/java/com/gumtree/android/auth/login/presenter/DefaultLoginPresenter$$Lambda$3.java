package com.gumtree.android.auth.login.presenter;

import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultLoginPresenter$$Lambda$3 implements Action1 {
    private final DefaultLoginPresenter arg$1;

    private DefaultLoginPresenter$$Lambda$3(DefaultLoginPresenter defaultLoginPresenter) {
        this.arg$1 = defaultLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultLoginPresenter defaultLoginPresenter) {
        return new DefaultLoginPresenter$$Lambda$3(defaultLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$login$2((AuthResult) obj);
    }
}
