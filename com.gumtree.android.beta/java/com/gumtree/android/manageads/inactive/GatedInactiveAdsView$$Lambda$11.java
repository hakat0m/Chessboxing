package com.gumtree.android.manageads.inactive;

import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedInactiveAdsView$$Lambda$11 implements Action {
    private final View arg$1;

    private GatedInactiveAdsView$$Lambda$11(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedInactiveAdsView$$Lambda$11(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPostAdActivity();
    }
}
