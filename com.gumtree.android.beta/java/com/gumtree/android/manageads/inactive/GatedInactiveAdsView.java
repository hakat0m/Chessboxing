package com.gumtree.android.manageads.inactive;

import android.support.annotation.Nullable;
import com.gumtree.android.manageads.Ad;
import com.gumtree.android.manageads.inactive.presenter.InactiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedInactiveAdsView implements View {
    private final Gate<Void> hideEmptyPage = new Gate();
    private final Gate<Void> hideInactiveAds = new Gate();
    private final Gate<Void> hideProgressBar = new Gate();
    private final Gate<Void> hideRefreshLayout = new Gate();
    private final Gate<Boolean> setHasMoreItems = new Gate();
    private final Gate<String> showEditAdActivity = new Gate();
    private final Gate<Void> showEmptyPage = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<List<Ad>> showInactiveAds = new Gate();
    private final Gate<Void> showLearnMorePage = new Gate();
    private final Gate<Void> showPostAdActivity = new Gate();
    private final Gate<Void> showProgressBar = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedInactiveAdsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showInactiveAds.open(GatedInactiveAdsView$$Lambda$2.lambdaFactory$(view));
        this.hideInactiveAds.open(GatedInactiveAdsView$$Lambda$3.lambdaFactory$(view));
        this.showError.open(GatedInactiveAdsView$$Lambda$4.lambdaFactory$(view));
        this.hideRefreshLayout.open(GatedInactiveAdsView$$Lambda$5.lambdaFactory$(view));
        this.setHasMoreItems.open(GatedInactiveAdsView$$Lambda$6.lambdaFactory$(view));
        this.showEmptyPage.open(GatedInactiveAdsView$$Lambda$7.lambdaFactory$(view));
        this.hideEmptyPage.open(GatedInactiveAdsView$$Lambda$8.lambdaFactory$(view));
        this.showProgressBar.open(GatedInactiveAdsView$$Lambda$9.lambdaFactory$(view));
        this.hideProgressBar.open(GatedInactiveAdsView$$Lambda$10.lambdaFactory$(view));
        this.showPostAdActivity.open(GatedInactiveAdsView$$Lambda$11.lambdaFactory$(view));
        this.showLearnMorePage.open(GatedInactiveAdsView$$Lambda$12.lambdaFactory$(view));
        this.showEditAdActivity.open(GatedInactiveAdsView$$Lambda$13.lambdaFactory$(view));
    }

    private void close() {
        this.showInactiveAds.close();
        this.hideInactiveAds.close();
        this.showError.close();
        this.hideRefreshLayout.close();
        this.setHasMoreItems.close();
        this.showEmptyPage.close();
        this.hideEmptyPage.close();
        this.showProgressBar.close();
        this.hideProgressBar.close();
        this.showPostAdActivity.close();
        this.showLearnMorePage.close();
        this.showEditAdActivity.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void showAds(List<Ad> list) {
        this.showInactiveAds.perform(list);
    }

    public void hideAds() {
        this.hideInactiveAds.perform(null);
    }

    public void showEditAdActivity(String str) {
        this.showEditAdActivity.perform(str);
    }

    public void hideRefreshLayout() {
        this.hideRefreshLayout.perform(null);
    }

    public void setHasMoreItems(boolean z) {
        this.setHasMoreItems.perform(Boolean.valueOf(z));
    }

    public void showEmptyPage() {
        this.showEmptyPage.perform(null);
    }

    public void hideEmptyPage() {
        this.hideEmptyPage.perform(null);
    }

    public void showProgressBar() {
        this.showProgressBar.perform(null);
    }

    public void hideProgressBar() {
        this.hideProgressBar.perform(null);
    }

    public void showPostAdActivity() {
        this.showPostAdActivity.perform(null);
    }

    public void showLearnMorePage() {
        this.showLearnMorePage.perform(null);
    }
}
