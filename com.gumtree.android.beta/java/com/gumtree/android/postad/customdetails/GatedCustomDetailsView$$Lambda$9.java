package com.gumtree.android.postad.customdetails;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedCustomDetailsView$$Lambda$9 implements Action {
    private final View arg$1;

    private GatedCustomDetailsView$$Lambda$9(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedCustomDetailsView$$Lambda$9(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.hideVrmManualEntry();
    }
}
