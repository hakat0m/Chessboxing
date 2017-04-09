package com.gumtree.android.auth.facebook.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultFacebookLoginPresenter$$Lambda$1 implements Action1 {
    private final DefaultFacebookLoginPresenter arg$1;

    private DefaultFacebookLoginPresenter$$Lambda$1(DefaultFacebookLoginPresenter defaultFacebookLoginPresenter) {
        this.arg$1 = defaultFacebookLoginPresenter;
    }

    public static Action1 lambdaFactory$(DefaultFacebookLoginPresenter defaultFacebookLoginPresenter) {
        return new DefaultFacebookLoginPresenter$$Lambda$1(defaultFacebookLoginPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
