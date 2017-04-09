package com.gumtree.android.login.registration;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnFocusChange;
import butterknife.OnTextChanged;
import com.apptentive.android.sdk.BuildConfig;
import com.gumtree.android.auth.registration.presenter.RegistrationPresenter;
import com.gumtree.android.auth.registration.presenter.RegistrationPresenter.View;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.login.di.AuthModule;
import com.gumtree.android.login.registration.views.PasswordStrengthTextView;
import com.gumtree.android.postad.views.EditTextSummaryValidationView;
import javax.inject.Inject;

public class RegistrationFragment extends BaseFragment implements View {
    @Bind({2131624574})
    android.view.View completeContent;
    @Bind({2131624566})
    EditTextSummaryValidationView email;
    @Bind({2131624571})
    EditTextSummaryValidationView firstName;
    @Bind({2131624563})
    android.view.View inputContent;
    @Bind({2131624572})
    EditTextSummaryValidationView lastName;
    @Bind({2131624568})
    EditText password;
    @Bind({2131624567})
    TextInputLayout passwordLayout;
    @Bind({2131624570})
    TextView passwordPrompt;
    @Inject
    RegistrationPresenter presenter;
    @Bind({2131624573})
    CheckBox promotions;
    @Bind({2131624565})
    ScrollView scrollView;
    @Bind({2131624575})
    TextView staticEmail;
    @Bind({2131624569})
    PasswordStrengthTextView strengthIndicator;

    @OnTextChanged({2131624568})
    void onPasswordChanged() {
        this.presenter.onPasswordChanged(this.password.getText().toString());
    }

    @OnFocusChange({2131624568})
    void onPasswordFocusChange(boolean z) {
        this.presenter.onPasswordFocusChange(z, this.password.getText().toString());
    }

    @OnClick({2131624564})
    void onRegisterClick() {
        hideKeyboard();
        this.presenter.register(this.email.getValue(), this.password.getText().toString(), this.firstName.getValue(), this.lastName.getValue(), this.promotions.isChecked());
    }

    @OnClick({2131624576})
    void onResendRegistrationClick() {
        this.presenter.onEditAndResendClick(this.email.getValue());
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903232, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        AuthModule.inject(this);
        ButterKnife.bind(this, view);
        this.passwordLayout.setErrorEnabled(true);
        this.email.onFocusChanged(RegistrationFragment$$Lambda$1.lambdaFactory$(this));
        this.email.setOnFieldValueChangeListener(RegistrationFragment$$Lambda$2.lambdaFactory$(this));
        this.firstName.onFocusChanged(RegistrationFragment$$Lambda$3.lambdaFactory$(this));
        this.firstName.setOnFieldValueChangeListener(RegistrationFragment$$Lambda$4.lambdaFactory$(this));
        this.lastName.onFocusChanged(RegistrationFragment$$Lambda$5.lambdaFactory$(this));
        this.lastName.setOnFieldValueChangeListener(RegistrationFragment$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$onViewCreated$0(android.view.View view, boolean z) {
        this.presenter.onEmailFocusChange(z, this.email.getValue());
    }

    /* synthetic */ void lambda$onViewCreated$1(String str) {
        this.presenter.onEmailChanged(str);
    }

    /* synthetic */ void lambda$onViewCreated$2(android.view.View view, boolean z) {
        this.presenter.onFirstNameFocusChange(z, this.firstName.getValue());
    }

    /* synthetic */ void lambda$onViewCreated$3(String str) {
        this.presenter.onFirstNameChanged(str);
    }

    /* synthetic */ void lambda$onViewCreated$4(android.view.View view, boolean z) {
        this.presenter.onLastNameFocusChange(z, this.lastName.getValue());
    }

    /* synthetic */ void lambda$onViewCreated$5(String str) {
        this.presenter.onLastNameChanged(str);
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
        this.presenter.onViewVisible(getUserVisibleHint());
    }

    public void setUserVisibleHint(boolean z) {
        super.setUserVisibleHint(z);
        if (this.presenter != null) {
            this.presenter.onViewVisible(z);
        }
    }

    public void onPause() {
        super.onPause();
        this.presenter.detachView();
    }

    public void onDestroy() {
        if (isAdded() && getActivity().isFinishing()) {
            this.presenter.destroy();
        }
        super.onDestroy();
    }

    public void showActivationPage(String str) {
        this.staticEmail.setText(str);
        this.inputContent.setVisibility(8);
        this.completeContent.setVisibility(0);
        hideKeyboard();
    }

    public void showInputPage() {
        this.inputContent.setVisibility(0);
        this.completeContent.setVisibility(8);
    }

    public void showValidEmail() {
        this.email.showError(null);
        this.email.showValid(true);
    }

    public void showEmailErrorMessage() {
        this.email.showError(getString(2131165434));
        this.email.showValid(false);
    }

    public void hideEmailErrorMessage() {
        this.email.reset();
        this.email.showValid(false);
    }

    public void showValidFirstName() {
        this.firstName.showError(null);
        this.firstName.showValid(true);
    }

    public void showFirstNameErrorMessage() {
        this.firstName.showError(getString(2131165431));
        this.firstName.showValid(false);
    }

    public void hideFirstNameErrorMessage() {
        this.firstName.reset();
        this.firstName.showValid(false);
    }

    public void showValidLastName() {
        this.lastName.showError(null);
        this.lastName.showValid(true);
    }

    public void showLastNameErrorMessage() {
        this.lastName.showError(getString(2131165432));
        this.lastName.showValid(false);
    }

    public void hideLastNameErrorMessage() {
        this.lastName.reset();
        this.lastName.showValid(false);
    }

    public void showPasswordErrorFeedback() {
        this.passwordLayout.setError(" ");
    }

    public void hidePasswordErrorFeedback() {
        this.passwordLayout.setError(null);
    }

    public void showPasswordStrength(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        this.strengthIndicator.update(passwordStrengthService$PasswordStrength);
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showProgress() {
        ProgressDialogFragment.show(getFragmentManager());
    }

    public void hideProgress() {
        ProgressDialogFragment.dismiss(getFragmentManager());
    }

    public void showCachedUserId(String str) {
        this.email.showValue(str);
    }

    public void showCachedPassword(String str) {
        this.password.setText(str);
        this.password.setSelection(str.length());
    }

    public void showCachedUserFullName(String str) {
        if (str == null) {
            this.firstName.showValue(BuildConfig.FLAVOR);
            this.lastName.showValue(BuildConfig.FLAVOR);
            return;
        }
        String[] split = str.split("\\s+", 2);
        if (split.length == 2) {
            this.firstName.showValue(split[0]);
            this.lastName.showValue(split[1]);
            return;
        }
        this.firstName.showValue(str);
        this.lastName.showValue(BuildConfig.FLAVOR);
    }

    public void showPasswordErrorPrompt() {
        this.passwordPrompt.setTextColor(getResources().getColor(2131493363));
    }

    public void hidePasswordErrorPrompt() {
        this.passwordPrompt.setTextColor(getResources().getColor(2131492910));
    }
}
