package com.gumtree.android.login.google;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter;
import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.common.fragments.BaseFragment;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.login.di.AuthModule;
import javax.inject.Inject;

public class GoogleLoginFragment extends BaseFragment implements View {
    @Bind({2131624476})
    ImageView background;
    @Bind({2131624479})
    TextView description;
    @Bind({2131624480})
    ImageView logo;
    @Inject
    GoogleLoginPresenter presenter;
    @Bind({2131624478})
    TextView title;

    @Nullable
    public android.view.View onCreateView(LayoutInflater layoutInflater, @Nullable ViewGroup viewGroup, @Nullable Bundle bundle) {
        return layoutInflater.inflate(2130903204, viewGroup, false);
    }

    public void onViewCreated(android.view.View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        AuthModule.inject(this);
        ButterKnife.bind(this, view);
    }

    public void onResume() {
        super.onResume();
        this.presenter.attachView(this);
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

    @OnClick({2131624481})
    void onGoogleClick() {
        this.presenter.onGoogleLoginClicked(getActivity());
    }

    @OnClick({2131624482})
    void onLoginClicked() {
        this.presenter.onLoginClicked();
    }

    @OnClick({2131624483})
    void onRegistrationClicked() {
        this.presenter.onRegistrationClicked(getActivity());
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
    }

    public void onAccountAdded(AuthIdentifier authIdentifier, AuthResult authResult) {
        Intent intent = new Intent();
        intent.putExtra("authAccount", authResult.getUserName());
        intent.putExtra("password", authResult.getPassWord());
        intent.putExtra("accountType", AppProvider.AUTHORITY);
        intent.putExtra("com.gumtree.android.auth.save_search", AuthIdentifier.SAVE_A_SEARCH == authIdentifier);
        intent.putExtra("displayname", authResult.getUserProfile().getDisplayName());
        getActivity().setResult(-1, intent);
    }

    public void showPreviousScreen() {
        finish();
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showNoNetwork() {
        showSnackBar(getString(2131165438));
    }

    public void showProgress() {
        ProgressDialogFragment.show(getFragmentManager());
    }

    public void hideProgress() {
        ProgressDialogFragment.dismiss(getFragmentManager());
    }

    public void showLogo() {
        this.title.setVisibility(8);
        this.description.setVisibility(8);
        this.logo.setVisibility(0);
        this.background.setImageResource(2131492902);
    }

    public void showPage(String str, String str2) {
        this.title.setText(str);
        this.description.setText(str2);
        this.title.setVisibility(0);
        this.description.setVisibility(0);
        this.logo.setVisibility(8);
        this.background.setImageResource(2130837643);
    }
}
