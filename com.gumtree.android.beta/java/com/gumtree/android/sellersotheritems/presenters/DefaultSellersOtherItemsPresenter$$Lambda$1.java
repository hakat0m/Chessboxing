package com.gumtree.android.sellersotheritems.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSellersOtherItemsPresenter$$Lambda$1 implements Action1 {
    private final GatedSellersOtherItemsView arg$1;

    private DefaultSellersOtherItemsPresenter$$Lambda$1(GatedSellersOtherItemsView gatedSellersOtherItemsView) {
        this.arg$1 = gatedSellersOtherItemsView;
    }

    public static Action1 lambdaFactory$(GatedSellersOtherItemsView gatedSellersOtherItemsView) {
        return new DefaultSellersOtherItemsPresenter$$Lambda$1(gatedSellersOtherItemsView);
    }

    @Hidden
    public void call(Object obj) {
        DefaultSellersOtherItemsPresenter.lambda$new$0(this.arg$1, (Throwable) obj);
    }
}
