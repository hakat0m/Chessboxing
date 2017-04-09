package com.gumtree.android.postad.dialogs;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class RetryPostDialog$$Lambda$1 implements SingleButtonCallback {
    private final RetryPostDialog arg$1;

    private RetryPostDialog$$Lambda$1(RetryPostDialog retryPostDialog) {
        this.arg$1 = retryPostDialog;
    }

    public static SingleButtonCallback lambdaFactory$(RetryPostDialog retryPostDialog) {
        return new RetryPostDialog$$Lambda$1(retryPostDialog);
    }

    @Hidden
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.arg$1.lambda$showDialog$0(materialDialog, dialogAction);
    }
}
