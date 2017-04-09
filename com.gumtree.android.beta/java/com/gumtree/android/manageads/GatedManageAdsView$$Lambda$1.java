package com.gumtree.android.manageads;

import com.gumtree.android.manageads.presenter.ManageAdsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedManageAdsView$$Lambda$1 implements Action1 {
    private final GatedManageAdsView arg$1;

    private GatedManageAdsView$$Lambda$1(GatedManageAdsView gatedManageAdsView) {
        this.arg$1 = gatedManageAdsView;
    }

    public static Action1 lambdaFactory$(GatedManageAdsView gatedManageAdsView) {
        return new GatedManageAdsView$$Lambda$1(gatedManageAdsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
