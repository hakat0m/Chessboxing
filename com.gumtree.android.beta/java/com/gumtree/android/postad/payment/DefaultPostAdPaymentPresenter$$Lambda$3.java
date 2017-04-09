package com.gumtree.android.postad.payment;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultPostAdPaymentPresenter$$Lambda$3 implements Action0 {
    private final DefaultPostAdPaymentPresenter arg$1;

    private DefaultPostAdPaymentPresenter$$Lambda$3(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        this.arg$1 = defaultPostAdPaymentPresenter;
    }

    public static Action0 lambdaFactory$(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        return new DefaultPostAdPaymentPresenter$$Lambda$3(defaultPostAdPaymentPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$handleOrderResult$2();
    }
}
