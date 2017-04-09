package com.gumtree.android.postad.payment;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdPaymentPresenter$$Lambda$5 implements Action1 {
    private static final DefaultPostAdPaymentPresenter$$Lambda$5 instance = new DefaultPostAdPaymentPresenter$$Lambda$5();

    private DefaultPostAdPaymentPresenter$$Lambda$5() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        DefaultPostAdPaymentPresenter.lambda$onPayPalSuccess$4((Throwable) obj);
    }
}
