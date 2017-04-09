package com.gumtree.android.sellersotheritems.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedSellersOtherItemsView$$Lambda$7 implements Action {
    private final View arg$1;

    private GatedSellersOtherItemsView$$Lambda$7(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedSellersOtherItemsView$$Lambda$7(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showTitle((String) obj);
    }
}
