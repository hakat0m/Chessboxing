package com.gumtree.android.auth.login;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.login.presenter.LoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.mvp.Gate;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedLoginView implements View {
    private final Gate<String> hideProgress = new Gate();
    private final Gate<Pair<AuthIdentifier, AuthResult>> showAuthResult = new Gate();
    private final Gate<String> showCachedPassword = new Gate();
    private final Gate<String> showCachedUserName = new Gate();
    private final Gate<Void> showEnterValidEmailError = new Gate();
    private final Gate<Void> showEnterValidPasswordError = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<String> showForgotPasswordActivity = new Gate();
    private final Gate<String> showHomeScreen = new Gate();
    private final Gate<Void> showNoNetworkError = new Gate();
    private final Gate<Void> showOnActivationPage = new Gate();
    private final Gate<Void> showPreviousScreen = new Gate();
    private final Gate<String> showProgress = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedLoginView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showCachedUserName.open(GatedLoginView$$Lambda$2.lambdaFactory$(view));
        this.showCachedPassword.open(GatedLoginView$$Lambda$3.lambdaFactory$(view));
        this.showAuthResult.open(GatedLoginView$$Lambda$4.lambdaFactory$(view));
        this.showError.open(GatedLoginView$$Lambda$5.lambdaFactory$(view));
        this.showNoNetworkError.open(GatedLoginView$$Lambda$6.lambdaFactory$(view));
        this.showEnterValidEmailError.open(GatedLoginView$$Lambda$7.lambdaFactory$(view));
        this.showEnterValidPasswordError.open(GatedLoginView$$Lambda$8.lambdaFactory$(view));
        this.showProgress.open(GatedLoginView$$Lambda$9.lambdaFactory$(view));
        this.hideProgress.open(GatedLoginView$$Lambda$10.lambdaFactory$(view));
        this.showForgotPasswordActivity.open(GatedLoginView$$Lambda$11.lambdaFactory$(view));
        this.showPreviousScreen.open(GatedLoginView$$Lambda$12.lambdaFactory$(view));
        this.showHomeScreen.open(GatedLoginView$$Lambda$13.lambdaFactory$(view));
        this.showOnActivationPage.open(GatedLoginView$$Lambda$14.lambdaFactory$(view));
    }

    private void close() {
        this.showCachedUserName.close();
        this.showCachedPassword.close();
        this.showAuthResult.close();
        this.showError.close();
        this.showNoNetworkError.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.showEnterValidEmailError.close();
        this.showEnterValidPasswordError.close();
        this.showForgotPasswordActivity.close();
        this.showPreviousScreen.close();
        this.showHomeScreen.close();
        this.showOnActivationPage.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showOnActivationPage() {
        this.showOnActivationPage.perform(null);
    }

    public void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult) {
        this.showAuthResult.perform(Pair.of(authIdentifier, authResult));
    }

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void showEnterValidEmailError() {
        this.showEnterValidEmailError.perform(null);
    }

    public void showEnterValidPasswordError() {
        this.showEnterValidPasswordError.perform(null);
    }

    public void showNoNetwork() {
        this.showNoNetworkError.perform(null);
    }

    public void showProgress() {
        this.showProgress.perform(null);
    }

    public void hideProgress() {
        this.hideProgress.perform(null);
    }

    public void showPreviousScreen() {
        this.showPreviousScreen.perform(null);
    }

    public void showHomeScreen(String str) {
        this.showHomeScreen.perform(str);
    }

    public void showForgotPasswordActivity(String str) {
        this.showForgotPasswordActivity.perform(str);
    }

    public void showCachedUserName(String str) {
        this.showCachedUserName.perform(str);
    }

    public void showCachedPassword(String str) {
        this.showCachedPassword.perform(str);
    }
}
