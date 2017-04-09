package com.gumtree.android.vip;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPSellerOtherItemsFragment$$Lambda$1 implements OnClickListener {
    private final VIPSellerOtherItemsFragment arg$1;

    private VIPSellerOtherItemsFragment$$Lambda$1(VIPSellerOtherItemsFragment vIPSellerOtherItemsFragment) {
        this.arg$1 = vIPSellerOtherItemsFragment;
    }

    public static OnClickListener lambdaFactory$(VIPSellerOtherItemsFragment vIPSellerOtherItemsFragment) {
        return new VIPSellerOtherItemsFragment$$Lambda$1(vIPSellerOtherItemsFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setSellerOtherItemsClickable$0(view);
    }
}
