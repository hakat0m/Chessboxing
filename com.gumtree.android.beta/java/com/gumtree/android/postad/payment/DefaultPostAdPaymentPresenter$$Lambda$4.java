package com.gumtree.android.postad.payment;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdPaymentPresenter$$Lambda$4 implements Action1 {
    private static final DefaultPostAdPaymentPresenter$$Lambda$4 instance = new DefaultPostAdPaymentPresenter$$Lambda$4();

    private DefaultPostAdPaymentPresenter$$Lambda$4() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        DefaultPostAdPaymentPresenter.lambda$onPayPalSuccess$3((Void) obj);
    }
}
