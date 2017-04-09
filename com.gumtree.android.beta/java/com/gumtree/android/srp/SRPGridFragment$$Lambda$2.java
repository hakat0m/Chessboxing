package com.gumtree.android.srp;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SRPGridFragment$$Lambda$2 implements OnClickListener {
    private final SRPGridFragment arg$1;

    private SRPGridFragment$$Lambda$2(SRPGridFragment sRPGridFragment) {
        this.arg$1 = sRPGridFragment;
    }

    public static OnClickListener lambdaFactory$(SRPGridFragment sRPGridFragment) {
        return new SRPGridFragment$$Lambda$2(sRPGridFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$1(view);
    }
}
