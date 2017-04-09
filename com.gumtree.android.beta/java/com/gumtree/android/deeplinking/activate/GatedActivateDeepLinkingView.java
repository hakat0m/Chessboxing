package com.gumtree.android.deeplinking.activate;

import android.support.annotation.Nullable;
import com.gumtree.android.deeplinking.activate.ActivateDeepLinkingPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedActivateDeepLinkingView implements View {
    private final Gate<String> showError = new Gate();
    private final Gate<Void> showHomeScreen = new Gate();
    private final Gate<Void> showLogin = new Gate();
    private final Gate<Void> showRegistration = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedActivateDeepLinkingView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showLogin.open(GatedActivateDeepLinkingView$$Lambda$4.lambdaFactory$(view));
        this.showHomeScreen.open(GatedActivateDeepLinkingView$$Lambda$5.lambdaFactory$(view));
        this.showRegistration.open(GatedActivateDeepLinkingView$$Lambda$6.lambdaFactory$(view));
        this.showError.open(GatedActivateDeepLinkingView$$Lambda$7.lambdaFactory$(view));
    }

    private void close() {
        this.showRegistration.close();
        this.showLogin.close();
        this.showHomeScreen.close();
        this.showError.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showHomeScreen() {
        this.showHomeScreen.perform(null);
    }

    public void showRegistration() {
        this.showRegistration.perform(null);
    }

    public void showLogin() {
        this.showLogin.perform(null);
    }

    public void showError(String str) {
        this.showError.perform(str);
    }
}
