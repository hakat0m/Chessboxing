package com.gumtree.android.sellersotheritems.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultSellersOtherItemsPresenter$$Lambda$6 implements Action0 {
    private final DefaultSellersOtherItemsPresenter arg$1;

    private DefaultSellersOtherItemsPresenter$$Lambda$6(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter) {
        this.arg$1 = defaultSellersOtherItemsPresenter;
    }

    public static Action0 lambdaFactory$(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter) {
        return new DefaultSellersOtherItemsPresenter$$Lambda$6(defaultSellersOtherItemsPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$onRefresh$5();
    }
}
