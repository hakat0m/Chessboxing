package com.gumtree.android.sellersotheritems;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SellersOtherItemsActivity$$Lambda$1 implements OnRefreshListener {
    private final SellersOtherItemsActivity arg$1;

    private SellersOtherItemsActivity$$Lambda$1(SellersOtherItemsActivity sellersOtherItemsActivity) {
        this.arg$1 = sellersOtherItemsActivity;
    }

    public static OnRefreshListener lambdaFactory$(SellersOtherItemsActivity sellersOtherItemsActivity) {
        return new SellersOtherItemsActivity$$Lambda$1(sellersOtherItemsActivity);
    }

    @Hidden
    public void onRefresh() {
        this.arg$1.lambda$onCreate$0();
    }
}
