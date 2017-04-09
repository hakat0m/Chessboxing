package com.gumtree.android.clients;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SoftUpdateFragment$$Lambda$2 implements OnClickListener {
    private final SoftUpdateFragment arg$1;

    private SoftUpdateFragment$$Lambda$2(SoftUpdateFragment softUpdateFragment) {
        this.arg$1 = softUpdateFragment;
    }

    public static OnClickListener lambdaFactory$(SoftUpdateFragment softUpdateFragment) {
        return new SoftUpdateFragment$$Lambda$2(softUpdateFragment);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$1(dialogInterface, i);
    }
}
