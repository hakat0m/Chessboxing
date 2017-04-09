package com.gumtree.android.vip_treebay;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$14 implements OnClickListener {
    private final VIPMainFragmentTreebay arg$1;

    private VIPMainFragmentTreebay$$Lambda$14(VIPMainFragmentTreebay vIPMainFragmentTreebay) {
        this.arg$1 = vIPMainFragmentTreebay;
    }

    public static OnClickListener lambdaFactory$(VIPMainFragmentTreebay vIPMainFragmentTreebay) {
        return new VIPMainFragmentTreebay$$Lambda$14(vIPMainFragmentTreebay);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$confirmDialog$13(dialogInterface, i);
    }
}
