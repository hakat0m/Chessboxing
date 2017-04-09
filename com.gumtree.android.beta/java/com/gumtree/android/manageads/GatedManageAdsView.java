package com.gumtree.android.manageads;

import android.support.annotation.Nullable;
import com.gumtree.android.manageads.presenter.ManageAdsPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedManageAdsView implements View {
    private final Gate<Void> refreshContent = new Gate();
    private final Gate<Void> showAdsActiveFragment = new Gate();
    private final Gate<Void> showAdsInactiveFragment = new Gate();
    private final Gate<Void> showLoginPage = new Gate();
    private final Gate<Void> showPostAdActivity = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedManageAdsView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showAdsActiveFragment.open(GatedManageAdsView$$Lambda$2.lambdaFactory$(view));
        this.showAdsInactiveFragment.open(GatedManageAdsView$$Lambda$3.lambdaFactory$(view));
        this.showLoginPage.open(GatedManageAdsView$$Lambda$4.lambdaFactory$(view));
        this.refreshContent.open(GatedManageAdsView$$Lambda$5.lambdaFactory$(view));
        this.showPostAdActivity.open(GatedManageAdsView$$Lambda$6.lambdaFactory$(view));
    }

    private void close() {
        this.showAdsActiveFragment.close();
        this.showAdsInactiveFragment.close();
        this.showLoginPage.close();
        this.refreshContent.close();
        this.showPostAdActivity.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showInactiveFragment() {
        this.showAdsInactiveFragment.perform(null);
    }

    public void showActiveFragment() {
        this.showAdsActiveFragment.perform(null);
    }

    public void showLoginPage() {
        this.showLoginPage.perform(null);
    }

    public void refreshContent() {
        this.refreshContent.perform(null);
    }

    public void showPostAdActivity() {
        this.showPostAdActivity.perform(null);
    }
}
