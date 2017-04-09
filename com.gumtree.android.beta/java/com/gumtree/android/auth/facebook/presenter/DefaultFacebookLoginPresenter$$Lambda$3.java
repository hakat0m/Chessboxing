package com.gumtree.android.auth.facebook.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultFacebookLoginPresenter$$Lambda$3 implements Action1 {
    private final DefaultFacebookLoginPresenter arg$1;

    private DefaultFacebookLoginPresenter$$Lambda$3(DefaultFacebookLoginPresenter defaultFacebookLoginPresenter) {
        this.arg$1 = defaultFacebookLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultFacebookLoginPresenter defaultFacebookLoginPresenter) {
        return new DefaultFacebookLoginPresenter$$Lambda$3(defaultFacebookLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$login$2((Throwable) obj);
    }
}
