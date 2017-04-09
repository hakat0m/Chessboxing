package com.gumtree.android.auth.login.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultLoginPresenter$$Lambda$8 implements Action1 {
    private static final DefaultLoginPresenter$$Lambda$8 instance = new DefaultLoginPresenter$$Lambda$8();

    private DefaultLoginPresenter$$Lambda$8() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        DefaultLoginPresenter.lambda$login$7((Throwable) obj);
    }
}
