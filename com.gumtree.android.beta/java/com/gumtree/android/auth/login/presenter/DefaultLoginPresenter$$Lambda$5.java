package com.gumtree.android.auth.login.presenter;

import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultLoginPresenter$$Lambda$5 implements Func1 {
    private final DefaultLoginPresenter arg$1;
    private final Object arg$2;

    private DefaultLoginPresenter$$Lambda$5(DefaultLoginPresenter defaultLoginPresenter, Object obj) {
        this.arg$1 = defaultLoginPresenter;
        this.arg$2 = obj;
    }

    public static Func1 lambdaFactory$(DefaultLoginPresenter defaultLoginPresenter, Object obj) {
        return new DefaultLoginPresenter$$Lambda$5(defaultLoginPresenter, obj);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$login$4(this.arg$2, (AuthResult) obj);
    }
}
