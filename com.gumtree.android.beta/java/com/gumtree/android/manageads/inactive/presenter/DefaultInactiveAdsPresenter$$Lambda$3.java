package com.gumtree.android.manageads.inactive.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultInactiveAdsPresenter$$Lambda$3 implements Action1 {
    private final DefaultInactiveAdsPresenter arg$1;

    private DefaultInactiveAdsPresenter$$Lambda$3(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        this.arg$1 = defaultInactiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        return new DefaultInactiveAdsPresenter$$Lambda$3(defaultInactiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$loadInactiveAds$2((NetworkState) obj);
    }
}
