package com.gumtree.android.vip_treebay;

import android.view.View;
import android.view.View.OnClickListener;
import com.gumtree.android.api.treebay.TreebayAd;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$1 implements OnClickListener {
    private final VIPMainFragmentTreebay arg$1;
    private final TreebayAd arg$2;

    private VIPMainFragmentTreebay$$Lambda$1(VIPMainFragmentTreebay vIPMainFragmentTreebay, TreebayAd treebayAd) {
        this.arg$1 = vIPMainFragmentTreebay;
        this.arg$2 = treebayAd;
    }

    public static OnClickListener lambdaFactory$(VIPMainFragmentTreebay vIPMainFragmentTreebay, TreebayAd treebayAd) {
        return new VIPMainFragmentTreebay$$Lambda$1(vIPMainFragmentTreebay, treebayAd);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$initialize$0(this.arg$2, view);
    }
}
