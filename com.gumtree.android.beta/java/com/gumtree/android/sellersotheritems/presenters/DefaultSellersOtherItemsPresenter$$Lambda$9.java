package com.gumtree.android.sellersotheritems.presenters;

import com.gumtree.android.sellersotheritems.models.SellerAdsPage;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSellersOtherItemsPresenter$$Lambda$9 implements Action1 {
    private final DefaultSellersOtherItemsPresenter arg$1;
    private final boolean arg$2;

    private DefaultSellersOtherItemsPresenter$$Lambda$9(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter, boolean z) {
        this.arg$1 = defaultSellersOtherItemsPresenter;
        this.arg$2 = z;
    }

    public static Action1 lambdaFactory$(DefaultSellersOtherItemsPresenter defaultSellersOtherItemsPresenter, boolean z) {
        return new DefaultSellersOtherItemsPresenter$$Lambda$9(defaultSellersOtherItemsPresenter, z);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$fetchSellerAdsPage$8(this.arg$2, (SellerAdsPage) obj);
    }
}
