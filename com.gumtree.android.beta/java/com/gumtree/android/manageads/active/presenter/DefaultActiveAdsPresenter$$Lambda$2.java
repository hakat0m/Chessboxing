package com.gumtree.android.manageads.active.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultActiveAdsPresenter$$Lambda$2 implements Action1 {
    private final DefaultActiveAdsPresenter arg$1;

    private DefaultActiveAdsPresenter$$Lambda$2(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        this.arg$1 = defaultActiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        return new DefaultActiveAdsPresenter$$Lambda$2(defaultActiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$1((List) obj);
    }
}
