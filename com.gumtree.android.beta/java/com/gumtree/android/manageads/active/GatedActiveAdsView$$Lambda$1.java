package com.gumtree.android.manageads.active;

import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedActiveAdsView$$Lambda$1 implements Action1 {
    private final GatedActiveAdsView arg$1;

    private GatedActiveAdsView$$Lambda$1(GatedActiveAdsView gatedActiveAdsView) {
        this.arg$1 = gatedActiveAdsView;
    }

    public static Action1 lambdaFactory$(GatedActiveAdsView gatedActiveAdsView) {
        return new GatedActiveAdsView$$Lambda$1(gatedActiveAdsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
