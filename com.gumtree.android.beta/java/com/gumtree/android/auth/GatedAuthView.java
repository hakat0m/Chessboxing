package com.gumtree.android.auth;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.presenter.AuthPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedAuthView implements View {
    private Gate<String> activated = new Gate();
    private Gate<Void> hideTabs = new Gate();
    private Gate<Void> login = new Gate();
    private Gate<Void> registration = new Gate();
    private Gate<Void> showHomeActivity = new Gate();
    private Gate<Void> showPreviousActivity = new Gate();
    private Gate<Void> showTabs = new Gate();
    private Gate<Boolean> splash = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedAuthView$$Lambda$1.lambdaFactory$(this));
    private Gate<String> toolbarTitle = new Gate();
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.toolbarTitle.open(GatedAuthView$$Lambda$2.lambdaFactory$(view));
        this.activated.open(GatedAuthView$$Lambda$3.lambdaFactory$(view));
        this.splash.open(GatedAuthView$$Lambda$4.lambdaFactory$(view));
        this.login.open(GatedAuthView$$Lambda$5.lambdaFactory$(view));
        this.registration.open(GatedAuthView$$Lambda$6.lambdaFactory$(view));
        this.showTabs.open(GatedAuthView$$Lambda$7.lambdaFactory$(view));
        this.hideTabs.open(GatedAuthView$$Lambda$8.lambdaFactory$(view));
        this.showPreviousActivity.open(GatedAuthView$$Lambda$9.lambdaFactory$(view));
        this.showHomeActivity.open(GatedAuthView$$Lambda$10.lambdaFactory$(view));
    }

    private void close() {
        this.toolbarTitle.close();
        this.activated.close();
        this.splash.close();
        this.login.close();
        this.registration.close();
        this.showTabs.close();
        this.showHomeActivity.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showSplash(boolean z) {
        this.splash.perform(Boolean.valueOf(z));
    }

    public void showLogin() {
        this.login.perform(null);
    }

    public void showRegistration() {
        this.registration.perform(null);
    }

    public void showTabs() {
        this.showTabs.perform(null);
    }

    public void hideTabs() {
        this.hideTabs.perform(null);
    }

    public void showPreviousActivity() {
        this.showPreviousActivity.perform(null);
    }

    public void showToolbarTitle(String str) {
        this.toolbarTitle.perform(str);
    }

    public void showUserActivationMessage(String str) {
        this.activated.perform(str);
    }

    public void showHomeActivity() {
        this.showHomeActivity.perform(null);
    }
}
