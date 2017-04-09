package com.gumtree.android.postad.payment;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdPaymentPresenter$$Lambda$7 implements Action1 {
    private final DefaultPostAdPaymentPresenter arg$1;

    private DefaultPostAdPaymentPresenter$$Lambda$7(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        this.arg$1 = defaultPostAdPaymentPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdPaymentPresenter defaultPostAdPaymentPresenter) {
        return new DefaultPostAdPaymentPresenter$$Lambda$7(defaultPostAdPaymentPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onPayPalSuccess$6((Long) obj);
    }
}
