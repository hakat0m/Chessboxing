package com.gumtree.android.manageads.active;

import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedActiveAdsView$$Lambda$11 implements Action {
    private final View arg$1;

    private GatedActiveAdsView$$Lambda$11(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedActiveAdsView$$Lambda$11(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPostAdActivity();
    }
}
