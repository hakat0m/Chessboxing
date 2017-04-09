package com.gumtree.android.postad.payment;

import com.gumtree.android.postad.PaymentData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdPaymentPresenter$$Lambda$1 implements Action1 {
    private final DefaultPostAdPaymentPresenter arg$1;

    private DefaultPostAdPaymentPresenter$$Lambda$1(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        this.arg$1 = defaultPostAdPaymentPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        return new DefaultPostAdPaymentPresenter$$Lambda$1(defaultPostAdPaymentPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$postOrders$0((PaymentData) obj);
    }
}
