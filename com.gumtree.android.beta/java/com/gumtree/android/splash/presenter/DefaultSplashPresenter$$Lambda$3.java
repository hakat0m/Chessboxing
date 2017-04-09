package com.gumtree.android.splash.presenter;

import com.gumtree.android.auth.google.model.SmartLockCredential;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultSplashPresenter$$Lambda$3 implements Func1 {
    private final DefaultSplashPresenter arg$1;
    private final Object arg$2;

    private DefaultSplashPresenter$$Lambda$3(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        this.arg$1 = defaultSplashPresenter;
        this.arg$2 = obj;
    }

    public static Func1 lambdaFactory$(DefaultSplashPresenter defaultSplashPresenter, Object obj) {
        return new DefaultSplashPresenter$$Lambda$3(defaultSplashPresenter, obj);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$performSmartLockRequest$2(this.arg$2, (SmartLockCredential) obj);
    }
}
