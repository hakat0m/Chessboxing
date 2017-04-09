package com.gumtree.android.post_ad.steps;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import com.gumtree.android.post_ad.steps.ChangeUserNameDialog.OnGoClickCallback;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ChangeUserNameDialog$$Lambda$1 implements OnClickListener {
    private final OnGoClickCallback arg$1;

    private ChangeUserNameDialog$$Lambda$1(OnGoClickCallback onGoClickCallback) {
        this.arg$1 = onGoClickCallback;
    }

    public static OnClickListener lambdaFactory$(OnGoClickCallback onGoClickCallback) {
        return new ChangeUserNameDialog$$Lambda$1(onGoClickCallback);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        ChangeUserNameDialog.lambda$onCreateDialog$0(this.arg$1, dialogInterface, i);
    }
}
