package com.gumtree.android.postad.payment.dialogs;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class PaymentAbortDialog$$Lambda$1 implements SingleButtonCallback {
    private final PaymentAbortDialog arg$1;

    private PaymentAbortDialog$$Lambda$1(PaymentAbortDialog paymentAbortDialog) {
        this.arg$1 = paymentAbortDialog;
    }

    public static SingleButtonCallback lambdaFactory$(PaymentAbortDialog paymentAbortDialog) {
        return new PaymentAbortDialog$$Lambda$1(paymentAbortDialog);
    }

    @Hidden
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.arg$1.lambda$showDialog$0(materialDialog, dialogAction);
    }
}
