package com.gumtree.android.splash.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSplashPresenter$$Lambda$2 implements Action1 {
    private final DefaultSplashPresenter arg$1;
    private final Object arg$2;

    private DefaultSplashPresenter$$Lambda$2(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        this.arg$1 = defaultSplashPresenter;
        this.arg$2 = obj;
    }

    public static Action1 lambdaFactory$(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        return new DefaultSplashPresenter$$Lambda$2(defaultSplashPresenter, obj);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$trySmartLockRequest$1(this.arg$2, (Throwable) obj);
    }
}
