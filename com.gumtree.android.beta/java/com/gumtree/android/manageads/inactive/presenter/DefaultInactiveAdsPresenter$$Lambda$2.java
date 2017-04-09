package com.gumtree.android.manageads.inactive.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultInactiveAdsPresenter$$Lambda$2 implements Action1 {
    private final DefaultInactiveAdsPresenter arg$1;

    private DefaultInactiveAdsPresenter$$Lambda$2(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        this.arg$1 = defaultInactiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter) {
        return new DefaultInactiveAdsPresenter$$Lambda$2(defaultInactiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$1((List) obj);
    }
}
