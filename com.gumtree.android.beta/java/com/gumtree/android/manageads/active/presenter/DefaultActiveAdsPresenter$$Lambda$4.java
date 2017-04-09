package com.gumtree.android.manageads.active.presenter;

import com.gumtree.android.manageads.Ads;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultActiveAdsPresenter$$Lambda$4 implements Action1 {
    private final DefaultActiveAdsPresenter arg$1;
    private final boolean arg$2;

    private DefaultActiveAdsPresenter$$Lambda$4(DefaultActiveAdsPresenter defaultActiveAdsPresenter, boolean z) {
        this.arg$1 = defaultActiveAdsPresenter;
        this.arg$2 = z;
    }

    public static Action1 lambdaFactory$(DefaultActiveAdsPresenter defaultActiveAdsPresenter, boolean z) {
        return new DefaultActiveAdsPresenter$$Lambda$4(defaultActiveAdsPresenter, z);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$loadActiveAds$3(this.arg$2, (Ads) obj);
    }
}
