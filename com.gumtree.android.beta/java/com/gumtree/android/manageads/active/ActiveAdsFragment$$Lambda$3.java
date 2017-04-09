package com.gumtree.android.manageads.active;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ActiveAdsFragment$$Lambda$3 implements OnClickListener {
    private static final ActiveAdsFragment$$Lambda$3 instance = new ActiveAdsFragment$$Lambda$3();

    private ActiveAdsFragment$$Lambda$3() {
    }

    public static OnClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        dialogInterface.cancel();
    }
}
