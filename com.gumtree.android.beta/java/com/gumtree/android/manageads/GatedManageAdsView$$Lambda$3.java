package com.gumtree.android.manageads;

import com.gumtree.android.manageads.presenter.ManageAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedManageAdsView$$Lambda$3 implements Action {
    private final View arg$1;

    private GatedManageAdsView$$Lambda$3(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedManageAdsView$$Lambda$3(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showInactiveFragment();
    }
}
