package com.gumtree.android.postad.contactdetails;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.afollestad.materialdialogs.MaterialDialog.SingleButtonCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ChangeContactEmailDialog$$Lambda$1 implements SingleButtonCallback {
    private final ChangeContactEmailDialog arg$1;

    private ChangeContactEmailDialog$$Lambda$1(ChangeContactEmailDialog changeContactEmailDialog) {
        this.arg$1 = changeContactEmailDialog;
    }

    public static SingleButtonCallback lambdaFactory$(ChangeContactEmailDialog changeContactEmailDialog) {
        return new ChangeContactEmailDialog$$Lambda$1(changeContactEmailDialog);
    }

    @Hidden
    public void onClick(MaterialDialog materialDialog, DialogAction dialogAction) {
        this.arg$1.lambda$showDialog$0(materialDialog, dialogAction);
    }
}
