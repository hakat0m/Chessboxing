package com.gumtree.android.manageads.active;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ActiveAdsFragment$$Lambda$2 implements OnClickListener {
    private final ActiveAdsFragment arg$1;
    private final String arg$2;

    private ActiveAdsFragment$$Lambda$2(ActiveAdsFragment activeAdsFragment, String str) {
        this.arg$1 = activeAdsFragment;
        this.arg$2 = str;
    }

    public static OnClickListener lambdaFactory$(ActiveAdsFragment activeAdsFragment, String str) {
        return new ActiveAdsFragment$$Lambda$2(activeAdsFragment, str);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$showDeleteConfirmationDialog$1(this.arg$2, dialogInterface, i);
    }
}
