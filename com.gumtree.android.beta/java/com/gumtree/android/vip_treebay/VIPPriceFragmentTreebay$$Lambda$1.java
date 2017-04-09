package com.gumtree.android.vip_treebay;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPPriceFragmentTreebay$$Lambda$1 implements OnClickListener {
    private final VIPPriceFragmentTreebay arg$1;
    private final String arg$2;
    private final String arg$3;

    private VIPPriceFragmentTreebay$$Lambda$1(VIPPriceFragmentTreebay vIPPriceFragmentTreebay, String str, String str2) {
        this.arg$1 = vIPPriceFragmentTreebay;
        this.arg$2 = str;
        this.arg$3 = str2;
    }

    public static OnClickListener lambdaFactory$(VIPPriceFragmentTreebay vIPPriceFragmentTreebay, String str, String str2) {
        return new VIPPriceFragmentTreebay$$Lambda$1(vIPPriceFragmentTreebay, str, str2);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setLocationListener$0(this.arg$2, this.arg$3, view);
    }
}
