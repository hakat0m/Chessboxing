package com.gumtree.android.manageads.inactive.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultInactiveAdsPresenter$$Lambda$5 implements Action1 {
    private final DefaultInactiveAdsPresenter arg$1;

    private DefaultInactiveAdsPresenter$$Lambda$5(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        this.arg$1 = defaultInactiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        return new DefaultInactiveAdsPresenter$$Lambda$5(defaultInactiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$loadInactiveAds$4((Throwable) obj);
    }
}
