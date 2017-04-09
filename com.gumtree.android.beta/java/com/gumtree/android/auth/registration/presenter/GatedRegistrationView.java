package com.gumtree.android.auth.registration.presenter;

import android.support.annotation.Nullable;
import com.gumtree.android.auth.registration.presenter.RegistrationPresenter.View;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.mvp.Gate;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedRegistrationView implements View {
    private final Gate<Void> hideEmailErrorMessage = new Gate();
    private final Gate<Void> hideFirstNameErrorMessage = new Gate();
    private final Gate<Void> hideLastNameErrorMessage = new Gate();
    private final Gate<Void> hidePasswordErrorMessage = new Gate();
    private final Gate<Void> hidePasswordErrorPrompt = new Gate();
    private Gate<String> hideProgress = new Gate();
    private final Gate<String> showActivationPage = new Gate();
    private final Gate<String> showCachedPassword = new Gate();
    private final Gate<String> showCachedUserFullName = new Gate();
    private final Gate<String> showCachedUserId = new Gate();
    private final Gate<Void> showEmailErrorMessage = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<Void> showFirstNameErrorMessage = new Gate();
    private final Gate<Void> showInputPage = new Gate();
    private final Gate<Void> showLastNameErrorMessage = new Gate();
    private final Gate<Void> showPasswordErrorMessage = new Gate();
    private final Gate<Void> showPasswordErrorPrompt = new Gate();
    private final Gate<PasswordStrengthService$PasswordStrength> showPasswordStrength = new Gate();
    private Gate<String> showProgress = new Gate();
    private final Gate<Void> showValidEmail = new Gate();
    private final Gate<Void> showValidFirstName = new Gate();
    private final Gate<Void> showValidLastName = new Gate();
    private Subscription subscription = this.trigger.subscribe(GatedRegistrationView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showActivationPage.open(GatedRegistrationView$$Lambda$2.lambdaFactory$(view));
        this.showCachedUserId.open(GatedRegistrationView$$Lambda$3.lambdaFactory$(view));
        this.showCachedPassword.open(GatedRegistrationView$$Lambda$4.lambdaFactory$(view));
        this.showCachedUserFullName.open(GatedRegistrationView$$Lambda$5.lambdaFactory$(view));
        this.showInputPage.open(GatedRegistrationView$$Lambda$6.lambdaFactory$(view));
        this.showPasswordStrength.open(GatedRegistrationView$$Lambda$7.lambdaFactory$(view));
        this.showProgress.open(GatedRegistrationView$$Lambda$8.lambdaFactory$(view));
        this.hideProgress.open(GatedRegistrationView$$Lambda$9.lambdaFactory$(view));
        this.showError.open(GatedRegistrationView$$Lambda$10.lambdaFactory$(view));
        this.showValidEmail.open(GatedRegistrationView$$Lambda$11.lambdaFactory$(view));
        this.showValidFirstName.open(GatedRegistrationView$$Lambda$12.lambdaFactory$(view));
        this.showValidLastName.open(GatedRegistrationView$$Lambda$13.lambdaFactory$(view));
        this.showFirstNameErrorMessage.open(GatedRegistrationView$$Lambda$14.lambdaFactory$(view));
        this.showLastNameErrorMessage.open(GatedRegistrationView$$Lambda$15.lambdaFactory$(view));
        this.showEmailErrorMessage.open(GatedRegistrationView$$Lambda$16.lambdaFactory$(view));
        this.showPasswordErrorMessage.open(GatedRegistrationView$$Lambda$17.lambdaFactory$(view));
        this.hideFirstNameErrorMessage.open(GatedRegistrationView$$Lambda$18.lambdaFactory$(view));
        this.hideLastNameErrorMessage.open(GatedRegistrationView$$Lambda$19.lambdaFactory$(view));
        this.hideEmailErrorMessage.open(GatedRegistrationView$$Lambda$20.lambdaFactory$(view));
        this.hidePasswordErrorMessage.open(GatedRegistrationView$$Lambda$21.lambdaFactory$(view));
        this.showPasswordErrorPrompt.open(GatedRegistrationView$$Lambda$22.lambdaFactory$(view));
        this.hidePasswordErrorPrompt.open(GatedRegistrationView$$Lambda$23.lambdaFactory$(view));
    }

    private void close() {
        this.showInputPage.close();
        this.showActivationPage.close();
        this.showPasswordStrength.close();
        this.showPasswordErrorMessage.close();
        this.showProgress.close();
        this.hideProgress.close();
        this.showError.close();
        this.showValidEmail.close();
        this.showValidFirstName.close();
        this.showValidLastName.close();
        this.showEmailErrorMessage.close();
        this.showFirstNameErrorMessage.close();
        this.showLastNameErrorMessage.close();
        this.hideEmailErrorMessage.close();
        this.hideFirstNameErrorMessage.close();
        this.hideLastNameErrorMessage.close();
        this.hidePasswordErrorMessage.close();
        this.showCachedPassword.close();
        this.showCachedUserId.close();
        this.showPasswordErrorPrompt.close();
        this.hidePasswordErrorPrompt.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    public void showActivationPage(String str) {
        this.showActivationPage.perform(str);
    }

    public void showInputPage() {
        this.showInputPage.perform(null);
    }

    public void showValidFirstName() {
        this.showValidFirstName.perform(null);
    }

    public void showValidLastName() {
        this.showValidLastName.perform(null);
    }

    public void showValidEmail() {
        this.showValidEmail.perform(null);
    }

    public void showEmailErrorMessage() {
        this.showEmailErrorMessage.perform(null);
    }

    public void hideEmailErrorMessage() {
        this.hideEmailErrorMessage.perform(null);
    }

    public void showFirstNameErrorMessage() {
        this.showFirstNameErrorMessage.perform(null);
    }

    public void hideFirstNameErrorMessage() {
        this.hideFirstNameErrorMessage.perform(null);
    }

    public void showLastNameErrorMessage() {
        this.showLastNameErrorMessage.perform(null);
    }

    public void hideLastNameErrorMessage() {
        this.hideLastNameErrorMessage.perform(null);
    }

    public void showPasswordErrorFeedback() {
        this.showPasswordErrorMessage.perform(null);
    }

    public void hidePasswordErrorFeedback() {
        this.hidePasswordErrorMessage.perform(null);
    }

    public void showPasswordStrength(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        this.showPasswordStrength.perform(passwordStrengthService$PasswordStrength);
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

    public void showCachedUserId(String str) {
        this.showCachedUserId.perform(str);
    }

    public void showCachedPassword(String str) {
        this.showCachedPassword.perform(str);
    }

    public void showCachedUserFullName(String str) {
        this.showCachedUserFullName.perform(str);
    }

    public void showPasswordErrorPrompt() {
        this.showPasswordErrorPrompt.perform(null);
    }

    public void hidePasswordErrorPrompt() {
        this.hidePasswordErrorPrompt.perform(null);
    }
}
