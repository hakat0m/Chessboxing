package com.gumtree.android.sellersotheritems.presenters;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.sellersotheritems.models.SellerAd;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter.View;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedSellersOtherItemsView implements View {
    private final Gate<String> openVIP = new Gate();
    private final Gate<List<SellerAd>> showAds = new Gate();
    private final Gate<String> showEmpty = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<Boolean> showMainLoading = new Gate();
    private final Gate<Boolean> showNextPageLoading = new Gate();
    private final Gate<Boolean> showRefreshLoading = new Gate();
    private final Gate<String> showSubtitle = new Gate();
    private final Gate<String> showTitle = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedSellersOtherItemsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    protected void open(View view) {
        this.showError.open(GatedSellersOtherItemsView$$Lambda$2.lambdaFactory$(view));
        this.showMainLoading.open(GatedSellersOtherItemsView$$Lambda$3.lambdaFactory$(view));
        this.showRefreshLoading.open(GatedSellersOtherItemsView$$Lambda$4.lambdaFactory$(view));
        this.showNextPageLoading.open(GatedSellersOtherItemsView$$Lambda$5.lambdaFactory$(view));
        this.showEmpty.open(GatedSellersOtherItemsView$$Lambda$6.lambdaFactory$(view));
        this.showTitle.open(GatedSellersOtherItemsView$$Lambda$7.lambdaFactory$(view));
        this.showSubtitle.open(GatedSellersOtherItemsView$$Lambda$8.lambdaFactory$(view));
        this.showAds.open(GatedSellersOtherItemsView$$Lambda$9.lambdaFactory$(view));
        this.openVIP.open(GatedSellersOtherItemsView$$Lambda$10.lambdaFactory$(view));
    }

    protected void close() {
        this.showError.close();
        this.showMainLoading.close();
        this.showRefreshLoading.close();
        this.showNextPageLoading.close();
        this.showEmpty.close();
        this.showTitle.close();
        this.showSubtitle.close();
        this.showAds.close();
        this.openVIP.close();
    }

    public void showTitle(String str) {
        this.showTitle.perform(str);
    }

    public void showSubtitle(String str) {
        this.showSubtitle.perform(str);
    }

    public void showAds(List<SellerAd> list) {
        this.showAds.perform(list);
    }

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void showMainLoading(boolean z) {
        this.showMainLoading.perform(Boolean.valueOf(z));
    }

    public void showRefreshLoading(boolean z) {
        this.showRefreshLoading.perform(Boolean.valueOf(z));
    }

    public void showNextPageLoading(boolean z) {
        this.showNextPageLoading.perform(Boolean.valueOf(z));
    }

    public void showEmpty(String str) {
        this.showEmpty.perform(str);
    }

    public void openVIP(String str) {
        this.openVIP.perform(str);
    }
}
