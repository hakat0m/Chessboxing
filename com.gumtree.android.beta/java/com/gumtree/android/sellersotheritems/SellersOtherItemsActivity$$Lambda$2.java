package com.gumtree.android.sellersotheritems;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SellersOtherItemsActivity$$Lambda$2 implements SellersOtherItemsClickListener {
    private final SellersOtherItemsActivity arg$1;

    private SellersOtherItemsActivity$$Lambda$2(SellersOtherItemsActivity sellersOtherItemsActivity) {
        this.arg$1 = sellersOtherItemsActivity;
    }

    public static SellersOtherItemsClickListener lambdaFactory$(SellersOtherItemsActivity sellersOtherItemsActivity) {
        return new SellersOtherItemsActivity$$Lambda$2(sellersOtherItemsActivity);
    }

    @Hidden
    public void onAdClicked(String str) {
        this.arg$1.lambda$initPaging$1(str);
    }
}
