package com.gumtree.android.login.forgotpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter;
import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter.View;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.login.forgotpassword.di.DaggerForgotPasswordComponent;
import com.gumtree.android.login.forgotpassword.di.ForgotPasswordComponent;
import com.gumtree.android.login.forgotpassword.di.ForgotPasswordModule;
import javax.inject.Inject;

public class ForgotPasswordActivity extends BaseActivity implements View {
    private static final String USER = "user";
    @Bind({2131624727})
    EditText email;
    @Bind({2131624133})
    android.view.View emailSentLayout;
    @Bind({2131624132})
    android.view.View forgotPasswordLayout;
    @Inject
    ForgotPasswordPresenter presenter;
    @Bind({2131624795})
    TextView resetPasswordEmail;

    public static Intent createIntent(String str, Context context) {
        Intent intent = new Intent(context, ForgotPasswordActivity.class);
        intent.putExtra(USER, str);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getForgotPasswordComponent().inject(this);
        setContentView(2130903080);
        ButterKnife.bind(this);
        this.email.setText(getIntent().getStringExtra(USER));
    }

    private ForgotPasswordComponent getForgotPasswordComponent() {
        ForgotPasswordComponent forgotPasswordComponent = (ForgotPasswordComponent) ComponentsManager.get().getBaseComponent(ForgotPasswordComponent.KEY);
        if (forgotPasswordComponent != null) {
            return forgotPasswordComponent;
        }
        Object build = DaggerForgotPasswordComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).forgotPasswordModule(new ForgotPasswordModule()).build();
        ComponentsManager.get().putBaseComponent(ForgotPasswordComponent.KEY, build);
        return build;
    }

    protected void onStart() {
        super.onStart();
        this.presenter.attachView(this);
    }

    protected void onStop() {
        super.onStop();
        this.presenter.detachView();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            ComponentsManager.get().removeBaseComponent(ForgotPasswordComponent.KEY);
            this.presenter.destroy();
        }
        super.onDestroy();
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onBackPressed();
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        hideKeyboard();
        this.presenter.back();
        super.onBackPressed();
    }

    @OnClick({2131624728})
    public void onResetPasswordClick() {
        this.presenter.forgotPassword(this.email.getText().toString().trim());
    }

    @OnEditorAction({2131624727})
    protected boolean passwordEditorAction(int i) {
        if (i != 6) {
            return false;
        }
        onResetPasswordClick();
        return true;
    }

    public void showError(String str) {
        showSnackBar(str);
    }

    public void showProgress() {
        ProgressDialogFragment.show(getSupportFragmentManager());
    }

    public void hideProgress() {
        ProgressDialogFragment.dismiss(getSupportFragmentManager());
    }

    public void showEmailSentPage(String str) {
        hideKeyboard();
        this.resetPasswordEmail.setText(getString(2131165766, new Object[]{str}));
        this.emailSentLayout.setVisibility(0);
        this.forgotPasswordLayout.setVisibility(8);
    }

    public void showForgotPasswordPage() {
        this.emailSentLayout.setVisibility(8);
        this.forgotPasswordLayout.setVisibility(0);
    }
}
