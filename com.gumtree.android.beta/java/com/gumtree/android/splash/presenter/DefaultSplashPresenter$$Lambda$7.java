package com.gumtree.android.splash.presenter;

import com.gumtree.android.auth.google.model.SmartLockCredential;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSplashPresenter$$Lambda$7 implements Action1 {
    private final DefaultSplashPresenter arg$1;
    private final Object arg$2;
    private final SmartLockCredential arg$3;

    private DefaultSplashPresenter$$Lambda$7(DefaultSplashPresenter defaultSplashPresenter, Object obj, SmartLockCredential smartLockCredential) {
        this.arg$1 = defaultSplashPresenter;
        this.arg$2 = obj;
        this.arg$3 = smartLockCredential;
    }

    public static Action1 lambdaFactory$(DefaultSplashPresenter defaultSplashPresenter, Object obj, SmartLockCredential smartLockCredential) {
        return new DefaultSplashPresenter$$Lambda$7(defaultSplashPresenter, obj, smartLockCredential);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$performSmartLockLoginAfterResolve$6(this.arg$2, this.arg$3, (Throwable) obj);
    }
}
