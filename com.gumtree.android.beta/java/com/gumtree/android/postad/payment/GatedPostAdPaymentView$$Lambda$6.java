package com.gumtree.android.postad.payment;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.payment.PostAdPaymentPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdPaymentView$$Lambda$6 implements Action {
    private final View arg$1;

    private GatedPostAdPaymentView$$Lambda$6(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdPaymentView$$Lambda$6(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPaymentIncludedInPackage();
    }
}
