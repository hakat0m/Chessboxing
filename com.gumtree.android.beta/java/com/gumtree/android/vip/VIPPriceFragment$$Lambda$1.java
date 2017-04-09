package com.gumtree.android.vip;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPPriceFragment$$Lambda$1 implements OnClickListener {
    private final VIPPriceFragment arg$1;
    private final String arg$2;
    private final String arg$3;

    private VIPPriceFragment$$Lambda$1(VIPPriceFragment vIPPriceFragment, String str, String str2) {
        this.arg$1 = vIPPriceFragment;
        this.arg$2 = str;
        this.arg$3 = str2;
    }

    public static OnClickListener lambdaFactory$(VIPPriceFragment vIPPriceFragment, String str, String str2) {
        return new VIPPriceFragment$$Lambda$1(vIPPriceFragment, str, str2);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setLocationListener$0(this.arg$2, this.arg$3, view);
    }
}
