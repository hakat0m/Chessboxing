package com.gumtree.android.splash;

import com.gumtree.android.splash.presenter.SplashPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedSplashView$$Lambda$1 implements Action1 {
    private final GatedSplashView arg$1;

    private GatedSplashView$$Lambda$1(GatedSplashView gatedSplashView) {
        this.arg$1 = gatedSplashView;
    }

    public static Action1 lambdaFactory$(GatedSplashView gatedSplashView) {
        return new GatedSplashView$$Lambda$1(gatedSplashView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
