package com.gumtree.android.manageads.active;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ActiveAdsFragment$$Lambda$4 implements OnCancelListener {
    private final ActiveAdsFragment arg$1;

    private ActiveAdsFragment$$Lambda$4(ActiveAdsFragment activeAdsFragment) {
        this.arg$1 = activeAdsFragment;
    }

    public static OnCancelListener lambdaFactory$(ActiveAdsFragment activeAdsFragment) {
        return new ActiveAdsFragment$$Lambda$4(activeAdsFragment);
    }

    @Hidden
    public void onCancel(DialogInterface dialogInterface) {
        this.arg$1.lambda$showDeleteConfirmationDialog$3(dialogInterface);
    }
}
