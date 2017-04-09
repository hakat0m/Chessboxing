package com.gumtree.android.manageads.inactive;

import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedInactiveAdsView$$Lambda$5 implements Action {
    private final View arg$1;

    private GatedInactiveAdsView$$Lambda$5(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedInactiveAdsView$$Lambda$5(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.hideRefreshLayout();
    }
}
