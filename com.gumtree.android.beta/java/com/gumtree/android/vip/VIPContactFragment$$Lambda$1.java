package com.gumtree.android.vip;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPContactFragment$$Lambda$1 implements OnClickListener {
    private final VIPContactFragment arg$1;

    private VIPContactFragment$$Lambda$1(VIPContactFragment vIPContactFragment) {
        this.arg$1 = vIPContactFragment;
    }

    public static OnClickListener lambdaFactory$(VIPContactFragment vIPContactFragment) {
        return new VIPContactFragment$$Lambda$1(vIPContactFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}
