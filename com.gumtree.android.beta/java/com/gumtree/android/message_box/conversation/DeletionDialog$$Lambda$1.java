package com.gumtree.android.message_box.conversation;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DeletionDialog$$Lambda$1 implements OnClickListener {
    private static final DeletionDialog$$Lambda$1 instance = new DeletionDialog$$Lambda$1();

    private DeletionDialog$$Lambda$1() {
    }

    public static OnClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        DeletionDialog.lambda$new$0(dialogInterface, i);
    }
}
