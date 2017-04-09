package com.gumtree.android.manageads.active.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultActiveAdsPresenter$$Lambda$1 implements Action1 {
    private final DefaultActiveAdsPresenter arg$1;

    private DefaultActiveAdsPresenter$$Lambda$1(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        this.arg$1 = defaultActiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        return new DefaultActiveAdsPresenter$$Lambda$1(defaultActiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$0((NetworkState) obj);
    }
}
