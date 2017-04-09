package com.gumtree.android.manageads.active;

import android.support.annotation.Nullable;
import com.gumtree.android.manageads.Ad;
import com.gumtree.android.manageads.active.presenter.ActiveAdsPresenter.View;
import com.gumtree.android.mvp.Gate;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedActiveAdsView implements View {
    private final Gate<Void> hideAds = new Gate();
    private final Gate<Void> hideEmptyPage = new Gate();
    private final Gate<Void> hideProgressBar = new Gate();
    private final Gate<Void> hideRefreshLayout = new Gate();
    private final Gate<Boolean> setHasMoreItems = new Gate();
    private final Gate<List<Ad>> showAds = new Gate();
    private final Gate<String> showDeleteConfirmationDialog = new Gate();
    private final Gate<String> showEditAdActivity = new Gate();
    private final Gate<Void> showEmptyPage = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<Void> showLearnMorePage = new Gate();
    private final Gate<Void> showPostAdActivity = new Gate();
    private final Gate<Void> showProgressBar = new Gate();
    private final Gate<String> showVIPActivity = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedActiveAdsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showAds.open(GatedActiveAdsView$$Lambda$2.lambdaFactory$(view));
        this.hideAds.open(GatedActiveAdsView$$Lambda$3.lambdaFactory$(view));
        this.showError.open(GatedActiveAdsView$$Lambda$4.lambdaFactory$(view));
        this.hideRefreshLayout.open(GatedActiveAdsView$$Lambda$5.lambdaFactory$(view));
        this.setHasMoreItems.open(GatedActiveAdsView$$Lambda$6.lambdaFactory$(view));
        this.showEmptyPage.open(GatedActiveAdsView$$Lambda$7.lambdaFactory$(view));
        this.hideEmptyPage.open(GatedActiveAdsView$$Lambda$8.lambdaFactory$(view));
        this.showProgressBar.open(GatedActiveAdsView$$Lambda$9.lambdaFactory$(view));
        this.hideProgressBar.open(GatedActiveAdsView$$Lambda$10.lambdaFactory$(view));
        this.showPostAdActivity.open(GatedActiveAdsView$$Lambda$11.lambdaFactory$(view));
        this.showVIPActivity.open(GatedActiveAdsView$$Lambda$12.lambdaFactory$(view));
        this.showLearnMorePage.open(GatedActiveAdsView$$Lambda$13.lambdaFactory$(view));
        this.showEditAdActivity.open(GatedActiveAdsView$$Lambda$14.lambdaFactory$(view));
        this.showDeleteConfirmationDialog.open(GatedActiveAdsView$$Lambda$15.lambdaFactory$(view));
    }

    private void close() {
        this.showAds.close();
        this.hideAds.close();
        this.showError.close();
        this.hideRefreshLayout.close();
        this.setHasMoreItems.close();
        this.showEmptyPage.close();
        this.hideEmptyPage.close();
        this.showProgressBar.close();
        this.hideProgressBar.close();
        this.showPostAdActivity.close();
        this.showVIPActivity.close();
        this.showLearnMorePage.close();
        this.showEditAdActivity.close();
        this.showDeleteConfirmationDialog.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showAds(List<Ad> list) {
        this.showAds.perform(list);
    }

    public void hideAds() {
        this.hideAds.perform(null);
    }

    public void showDeleteConfirmationDialog(String str) {
        this.showDeleteConfirmationDialog.perform(str);
    }

    public void showError(String str) {
        this.showError.perform(str);
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

    public void showVIPActivity(String str) {
        this.showVIPActivity.perform(str);
    }

    public void showLearnMorePage() {
        this.showLearnMorePage.perform(null);
    }

    public void showEditAdActivity(String str) {
        this.showEditAdActivity.perform(str);
    }
}
