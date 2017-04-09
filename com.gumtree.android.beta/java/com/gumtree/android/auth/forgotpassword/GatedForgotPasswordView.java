package com.gumtree.android.auth.forgotpassword;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter.View;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedForgotPasswordView implements View {
    private Gate<String> hideProgress = new Gate();
    private Gate<String> showEmailSentPage = new Gate();
    private Gate<String> showError = new Gate();
    private Gate<Void> showForgotPasswordPage = new Gate();
    private Gate<String> showProgress = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedForgotPasswordView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showError.open(GatedForgotPasswordView$$Lambda$4.lambdaFactory$(view));
        this.showProgress.open(GatedForgotPasswordView$$Lambda$5.lambdaFactory$(view));
        this.hideProgress.open(GatedForgotPasswordView$$Lambda$6.lambdaFactory$(view));
        this.showEmailSentPage.open(GatedForgotPasswordView$$Lambda$7.lambdaFactory$(view));
        this.showForgotPasswordPage.open(GatedForgotPasswordView$$Lambda$8.lambdaFactory$(view));
    }

    private void close() {
        this.showError.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.showEmailSentPage.close();
        this.showForgotPasswordPage.close();
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

    public void showProgress() {
        this.showProgress.perform(null);
    }

    public void hideProgress() {
        this.hideProgress.perform(null);
    }

    public void showEmailSentPage(String str) {
        this.showEmailSentPage.perform(str);
    }

    public void showForgotPasswordPage() {
        this.showForgotPasswordPage.perform(null);
    }
}
