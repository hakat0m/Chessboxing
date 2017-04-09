package com.gumtree.android.postad.payment;

import com.gumtree.android.postad.payment.dialogs.PaymentAbortDialog.Listener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PostAdPaymentActivity$$Lambda$1 implements Listener {
    private final PostAdPaymentActivity arg$1;

    private PostAdPaymentActivity$$Lambda$1(PostAdPaymentActivity postAdPaymentActivity) {
        this.arg$1 = postAdPaymentActivity;
    }

    public static Listener lambdaFactory$(PostAdPaymentActivity postAdPaymentActivity) {
        return new PostAdPaymentActivity$$Lambda$1(postAdPaymentActivity);
    }

    @Hidden
    public void onAbortConfirmed() {
        this.arg$1.lambda$showAbortDialog$0();
    }
}
