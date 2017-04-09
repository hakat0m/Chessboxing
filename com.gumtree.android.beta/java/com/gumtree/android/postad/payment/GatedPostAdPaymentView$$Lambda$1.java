package com.gumtree.android.postad.payment;

import com.gumtree.android.postad.payment.PostAdPaymentPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPostAdPaymentView$$Lambda$1 implements Action1 {
    private final GatedPostAdPaymentView arg$1;

    private GatedPostAdPaymentView$$Lambda$1(GatedPostAdPaymentView gatedPostAdPaymentView) {
        this.arg$1 = gatedPostAdPaymentView;
    }

    public static Action1 lambdaFactory$(GatedPostAdPaymentView gatedPostAdPaymentView) {
        return new GatedPostAdPaymentView$$Lambda$1(gatedPostAdPaymentView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
