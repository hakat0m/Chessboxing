package com.gumtree.android.terms;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class TermsDialog$$Lambda$1 implements OnClickListener {
    private final TermsDialog arg$1;

    private TermsDialog$$Lambda$1(TermsDialog termsDialog) {
        this.arg$1 = termsDialog;
    }

    public static OnClickListener lambdaFactory$(TermsDialog termsDialog) {
        return new TermsDialog$$Lambda$1(termsDialog);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$0(dialogInterface, i);
    }
}
