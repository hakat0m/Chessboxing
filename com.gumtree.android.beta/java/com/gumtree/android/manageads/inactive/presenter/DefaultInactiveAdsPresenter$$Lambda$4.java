package com.gumtree.android.manageads.inactive.presenter;

import com.gumtree.android.manageads.Ads;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultInactiveAdsPresenter$$Lambda$4 implements Action1 {
    private final DefaultInactiveAdsPresenter arg$1;
    private final boolean arg$2;

    private DefaultInactiveAdsPresenter$$Lambda$4(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter, boolean z) {
        this.arg$1 = defaultInactiveAdsPresenter;
        this.arg$2 = z;
    }

    public static Action1 lambdaFactory$(DefaultInactiveAdsPresenter defaultInactiveAdsPresenter, boolean z) {
        return new DefaultInactiveAdsPresenter$$Lambda$4(defaultInactiveAdsPresenter, z);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$loadInactiveAds$3(this.arg$2, (Ads) obj);
    }
}
