package com.gumtree.android.auth.google;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.mvp.Gate;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedGoogleLoginView implements View {
    private Gate<String> hideProgress = new Gate();
    private Gate<Pair<AuthIdentifier, AuthResult>> showAuthResult = new Gate();
    private Gate<String> showError = new Gate();
    private Gate<Void> showLogo = new Gate();
    private final Gate<Void> showNoNetworkError = new Gate();
    private Gate<Pair<String, String>> showPage = new Gate();
    private Gate<String> showPageDescription = new Gate();
    private Gate<Void> showPreviousScreen = new Gate();
    private Gate<String> showProgress = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedGoogleLoginView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showAuthResult.open(GatedGoogleLoginView$$Lambda$2.lambdaFactory$(view));
        this.showError.open(GatedGoogleLoginView$$Lambda$3.lambdaFactory$(view));
        this.showNoNetworkError.open(GatedGoogleLoginView$$Lambda$4.lambdaFactory$(view));
        this.showPage.open(GatedGoogleLoginView$$Lambda$5.lambdaFactory$(view));
        this.showLogo.open(GatedGoogleLoginView$$Lambda$6.lambdaFactory$(view));
        this.showProgress.open(GatedGoogleLoginView$$Lambda$7.lambdaFactory$(view));
        this.hideProgress.open(GatedGoogleLoginView$$Lambda$8.lambdaFactory$(view));
        this.showPreviousScreen.open(GatedGoogleLoginView$$Lambda$9.lambdaFactory$(view));
    }

    private void close() {
        this.showAuthResult.close();
        this.showError.close();
        this.showNoNetworkError.close();
        this.showPage.close();
        this.showLogo.close();
        this.showPageDescription.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.showPreviousScreen.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult) {
        this.showAuthResult.perform(Pair.of(authIdentifier, authResult));
    }

    public void showPreviousScreen() {
        this.showPreviousScreen.perform(null);
    }

    public void showError(String str) {
        this.showError.perform(str);
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

    public void showLogo() {
        this.showLogo.perform(null);
    }

    public void showPage(String str, String str2) {
        this.showPage.perform(Pair.of(str, str2));
    }
}
