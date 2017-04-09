package com.gumtree.android.manageads.active;

import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedActiveAdsView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedActiveAdsView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedActiveAdsView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showError((String) obj);
    }
}
