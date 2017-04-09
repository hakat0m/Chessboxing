package com.gumtree.android.splash.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSplashPresenter$$Lambda$5 implements Action1 {
    private final DefaultSplashPresenter arg$1;
    private final Object arg$2;

    private DefaultSplashPresenter$$Lambda$5(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        this.arg$1 = defaultSplashPresenter;
        this.arg$2 = obj;
    }

    public static Action1 lambdaFactory$(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        return new DefaultSplashPresenter$$Lambda$5(defaultSplashPresenter, obj);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$performSmartLockRequest$4(this.arg$2, (Throwable) obj);
    }
}
