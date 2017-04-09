package com.gumtree.android.login.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.login.presenter.LoginPresenter;
import com.gumtree.android.auth.login.presenter.LoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.login.ActivationDialogFragment;
import com.gumtree.android.login.di.AuthModule;
import com.gumtree.android.login.forgotpassword.ForgotPasswordActivity;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;
import javax.inject.Inject;

public class LoginFragment extends BaseFragment implements View {
    @Bind({2131624109})
    ShowHidePasswordEditText password;
    @Inject
    LoginPresenter presenter;
    @Bind({2131624108})
    EditText username;

    @OnTextChanged({2131624108})
    void onUsernameChanged(CharSequence charSequence) {
        this.presenter.onUsernameChanged(charSequence.toString());
    }

    @OnTextChanged({2131624109})
    void onPasswordChanged(CharSequence charSequence) {
        this.presenter.onPasswordChanged(charSequence.toString());
    }

    @OnEditorAction({2131624109})
    protected boolean passwordEditorAction(int i) {
        if (i != 6) {
            return false;
        }
        onLoginClick();
        return true;
    }

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903214, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        AuthModule.inject(this);
        ButterKnife.bind(this, view);
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

    @OnClick({2131624511})
    void onLoginClick() {
        this.presenter.login(getActivity(), this.username.getText().toString().trim(), this.password.getText().toString());
    }

    @OnClick({2131624111})
    void onForgotPassClick() {
        String str = null;
        if (!TextUtils.isEmpty(this.username.getText())) {
            str = this.username.getText().toString().trim();
        }
        this.presenter.forgotPassword(str);
    }

    public void showOnActivationPage() {
        ActivationDialogFragment.show(getFragmentManager());
    }

    public void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult) {
        hideKeyboard();
        Intent intent = new Intent();
        intent.putExtra("authAccount", authResult.getUserName());
        intent.putExtra("password", authResult.getPassWord());
        intent.putExtra("accountType", AppProvider.AUTHORITY);
        intent.putExtra("com.gumtree.android.auth.save_search", AuthIdentifier.SAVE_A_SEARCH == authIdentifier);
        intent.putExtra("displayname", authResult.getUserProfile().getDisplayName());
        getActivity().setResult(-1, intent);
    }

    public void onDestroy() {
        if (isAdded() && getActivity().isFinishing()) {
            this.presenter.destroy();
        }
        super.onDestroy();
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showEnterValidEmailError() {
        showSnackBar(getString(2131165434));
    }

    public void showEnterValidPasswordError() {
        showSnackBar(getString(2131165433));
    }

    public void showNoNetwork() {
        showSnackBar(getString(2131165438));
    }

    public void showProgress() {
        ProgressDialogFragment.show(getFragmentManager());
    }

    public void hideProgress() {
        hideKeyboard();
        ProgressDialogFragment.dismiss(getFragmentManager());
    }

    public void showPreviousScreen() {
        finish();
    }

    public void showHomeScreen(String str) {
        startActivity(HomeActivity.createIntent(str, getActivity()));
        finish();
    }

    public void showForgotPasswordActivity(String str) {
        startActivity(ForgotPasswordActivity.createIntent(str, getActivity()));
    }

    public void showCachedUserName(String str) {
        this.username.setText(str);
        this.username.setSelection(str.length());
    }

    public void showCachedPassword(String str) {
        this.password.setText(str);
        this.password.setSelection(this.password.length());
    }
}
