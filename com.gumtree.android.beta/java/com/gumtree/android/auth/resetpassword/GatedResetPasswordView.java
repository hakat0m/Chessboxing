package com.gumtree.android.auth.resetpassword;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter.View;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedResetPasswordView implements View {
    private final Gate<Void> hidePasswordError = new Gate();
    private Gate<String> hideProgress = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<String> showLoginPage = new Gate();
    private final Gate<Void> showPasswordError = new Gate();
    private final Gate<PasswordStrengthService$PasswordStrength> showPasswordStrength = new Gate();
    private Gate<String> showProgress = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedResetPasswordView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showLoginPage.open(GatedResetPasswordView$$Lambda$2.lambdaFactory$(view));
        this.showError.open(GatedResetPasswordView$$Lambda$3.lambdaFactory$(view));
        this.showPasswordError.open(GatedResetPasswordView$$Lambda$4.lambdaFactory$(view));
        this.hidePasswordError.open(GatedResetPasswordView$$Lambda$5.lambdaFactory$(view));
        this.showPasswordStrength.open(GatedResetPasswordView$$Lambda$6.lambdaFactory$(view));
        this.showProgress.open(GatedResetPasswordView$$Lambda$7.lambdaFactory$(view));
        this.hideProgress.open(GatedResetPasswordView$$Lambda$8.lambdaFactory$(view));
    }

    private void close() {
        this.showLoginPage.close();
        this.showError.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.showPasswordError.close();
        this.hidePasswordError.close();
        this.showPasswordStrength.close();
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

    public void showLoginFragment() {
        this.showLoginPage.perform(null);
    }

    public void showPasswordError() {
        this.showPasswordError.perform(null);
    }

    public void hidePasswordError() {
        this.hidePasswordError.perform(null);
    }

    public void showPasswordStrength(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        this.showPasswordStrength.perform(passwordStrengthService$PasswordStrength);
    }
}
