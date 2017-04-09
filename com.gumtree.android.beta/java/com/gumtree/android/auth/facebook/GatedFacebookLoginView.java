package com.gumtree.android.auth.facebook;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.mvp.Gate;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedFacebookLoginView implements View {
    private Gate<Void> handleFBLogout = new Gate();
    private Gate<String> hideProgress = new Gate();
    private Gate<Pair<AuthIdentifier, AuthResult>> showAuthResult = new Gate();
    private Gate<String> showError = new Gate();
    private final Gate<Void> showNoNetworkError = new Gate();
    private Gate<String> showProgress = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedFacebookLoginView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showAuthResult.open(GatedFacebookLoginView$$Lambda$2.lambdaFactory$(view));
        this.showError.open(GatedFacebookLoginView$$Lambda$3.lambdaFactory$(view));
        this.showProgress.open(GatedFacebookLoginView$$Lambda$4.lambdaFactory$(view));
        this.hideProgress.open(GatedFacebookLoginView$$Lambda$5.lambdaFactory$(view));
        this.showNoNetworkError.open(GatedFacebookLoginView$$Lambda$6.lambdaFactory$(view));
        this.handleFBLogout.open(GatedFacebookLoginView$$Lambda$7.lambdaFactory$(view));
    }

    private void close() {
        this.showAuthResult.close();
        this.showError.close();
        this.showNoNetworkError.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.handleFBLogout.close();
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

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void showNoNetworkError() {
        this.showNoNetworkError.perform(null);
    }

    public void showProgress() {
        this.showProgress.perform(null);
    }

    public void hideProgress() {
        this.hideProgress.perform(null);
    }

    public void handleFBLogout() {
        this.handleFBLogout.perform(null);
    }
}
