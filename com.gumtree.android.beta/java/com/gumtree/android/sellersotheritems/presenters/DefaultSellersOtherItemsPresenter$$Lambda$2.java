package com.gumtree.android.sellersotheritems.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultSellersOtherItemsPresenter$$Lambda$2 implements Action1 {
    private final DefaultSellersOtherItemsPresenter arg$1;

    private DefaultSellersOtherItemsPresenter$$Lambda$2(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter) {
        this.arg$1 = defaultSellersOtherItemsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter) {
        return new DefaultSellersOtherItemsPresenter$$Lambda$2(defaultSellersOtherItemsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$1((List) obj);
    }
}
