package com.gumtree.android.sellersotheritems.presenters;

import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedSellersOtherItemsView$$Lambda$1 implements Action1 {
    private final GatedSellersOtherItemsView arg$1;

    private GatedSellersOtherItemsView$$Lambda$1(GatedSellersOtherItemsView gatedSellersOtherItemsView) {
        this.arg$1 = gatedSellersOtherItemsView;
    }

    public static Action1 lambdaFactory$(GatedSellersOtherItemsView gatedSellersOtherItemsView) {
        return new GatedSellersOtherItemsView$$Lambda$1(gatedSellersOtherItemsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
