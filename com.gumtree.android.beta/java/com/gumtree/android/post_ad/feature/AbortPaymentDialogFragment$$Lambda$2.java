package com.gumtree.android.post_ad.feature;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class AbortPaymentDialogFragment$$Lambda$2 implements OnClickListener {
    private final AbortPaymentDialogFragment arg$1;

    private AbortPaymentDialogFragment$$Lambda$2(AbortPaymentDialogFragment abortPaymentDialogFragment) {
        this.arg$1 = abortPaymentDialogFragment;
    }

    public static OnClickListener lambdaFactory$(AbortPaymentDialogFragment abortPaymentDialogFragment) {
        return new AbortPaymentDialogFragment$$Lambda$2(abortPaymentDialogFragment);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$1(dialogInterface, i);
    }
}
