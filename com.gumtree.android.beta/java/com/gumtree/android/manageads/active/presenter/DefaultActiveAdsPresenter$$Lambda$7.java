package com.gumtree.android.manageads.active.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import retrofit.client.Response;
import rx.functions.Action1;

final /* synthetic */ class DefaultActiveAdsPresenter$$Lambda$7 implements Action1 {
    private final DefaultActiveAdsPresenter arg$1;

    private DefaultActiveAdsPresenter$$Lambda$7(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        this.arg$1 = defaultActiveAdsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultActiveAdsPresenter defaultActiveAdsPresenter) {
        return new DefaultActiveAdsPresenter$$Lambda$7(defaultActiveAdsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$deleteAd$6((Response) obj);
    }
}
