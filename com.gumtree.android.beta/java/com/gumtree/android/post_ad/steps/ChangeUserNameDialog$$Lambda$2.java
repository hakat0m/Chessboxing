package com.gumtree.android.post_ad.steps;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ChangeUserNameDialog$$Lambda$2 implements OnClickListener {
    private static final ChangeUserNameDialog$$Lambda$2 instance = new ChangeUserNameDialog$$Lambda$2();

    private ChangeUserNameDialog$$Lambda$2() {
    }

    public static OnClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        ChangeUserNameDialog.lambda$onCreateDialog$1(dialogInterface, i);
    }
}
