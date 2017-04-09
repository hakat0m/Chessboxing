package com.gumtree.android.sellersotheritems.presenters;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import com.gumtree.android.sellersotheritems.models.SellerAd;
import java.util.List;

public interface SellersOtherItemsPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void openVIP(String str);

        void showAds(List<SellerAd> list);

        void showEmpty(@Nullable String str);

        void showError(String str);

        void showMainLoading(boolean z);

        void showNextPageLoading(boolean z);

        void showRefreshLoading(boolean z);

        void showSubtitle(String str);

        void showTitle(String str);
    }

    void onAdClicked(String str);

    void onBottomPageReached();

    void onLoad();

    void onRefresh();
}
