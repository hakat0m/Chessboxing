package com.gumtree.android.splash.presenter;

import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSplashPresenter$$Lambda$4 implements Action1 {
    private final DefaultSplashPresenter arg$1;
    private final Object arg$2;

    private DefaultSplashPresenter$$Lambda$4(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        this.arg$1 = defaultSplashPresenter;
        this.arg$2 = obj;
    }

    public static Action1 lambdaFactory$(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        return new DefaultSplashPresenter$$Lambda$4(defaultSplashPresenter, obj);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$performSmartLockRequest$3(this.arg$2, (AuthResult) obj);
    }
}
