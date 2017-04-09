package com.gumtree.android.manageads.active.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultActiveAdsPresenter$$Lambda$6 implements Action0 {
    private final DefaultActiveAdsPresenter arg$1;

    private DefaultActiveAdsPresenter$$Lambda$6(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        this.arg$1 = defaultActiveAdsPresenter;
    }

    public static Action0 lambdaFactory$(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        return new DefaultActiveAdsPresenter$$Lambda$6(defaultActiveAdsPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$deleteAd$5();
    }
}
