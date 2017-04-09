package com.gumtree.android.manageads.inactive;

import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;

final /* synthetic */ class GatedInactiveAdsView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedInactiveAdsView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedInactiveAdsView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showAds((List) obj);
    }
}
