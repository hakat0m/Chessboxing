package com.gumtree.android.manageads.inactive;

import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedInactiveAdsView$$Lambda$1 implements Action1 {
    private final GatedInactiveAdsView arg$1;

    private GatedInactiveAdsView$$Lambda$1(GatedInactiveAdsView gatedInactiveAdsView) {
        this.arg$1 = gatedInactiveAdsView;
    }

    public static Action1 lambdaFactory$(GatedInactiveAdsView gatedInactiveAdsView) {
        return new GatedInactiveAdsView$$Lambda$1(gatedInactiveAdsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
