package com.gumtree.android.common.fragments;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class NeverAskAgainPermissionsDialogFragment$$Lambda$1 implements OnClickListener {
    private final NeverAskAgainPermissionsDialogFragment arg$1;

    private NeverAskAgainPermissionsDialogFragment$$Lambda$1(NeverAskAgainPermissionsDialogFragment neverAskAgainPermissionsDialogFragment) {
        this.arg$1 = neverAskAgainPermissionsDialogFragment;
    }

    public static OnClickListener lambdaFactory$(NeverAskAgainPermissionsDialogFragment neverAskAgainPermissionsDialogFragment) {
        return new NeverAskAgainPermissionsDialogFragment$$Lambda$1(neverAskAgainPermissionsDialogFragment);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$0(dialogInterface, i);
    }
}
