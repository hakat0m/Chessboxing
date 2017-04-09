package com.gumtree.android.splash;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.splash.presenter.SplashPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedSplashView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedSplashView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedSplashView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showSmartLockLoginFailure();
    }
}
