package com.gumtree.android.sellersotheritems.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;

final /* synthetic */ class GatedSellersOtherItemsView$$Lambda$9 implements Action {
    private final View arg$1;

    private GatedSellersOtherItemsView$$Lambda$9(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedSellersOtherItemsView$$Lambda$9(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showAds((List) obj);
    }
}
