package com.gumtree.android.postad.customdetails;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter.View;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedCustomDetailsView$$Lambda$3 implements Action {
    private final View arg$1;

    private GatedCustomDetailsView$$Lambda$3(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedCustomDetailsView$$Lambda$3(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.closingScreen((CustomDetailsData) obj);
    }
}
