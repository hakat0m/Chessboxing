package com.gumtree.android.login.resetpassword;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnEditorAction;
import butterknife.OnTextChanged;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.presenter.NavView;
import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter;
import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter.View;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.fragments.ProgressDialogFragment;
import com.gumtree.android.common.utils.ThemeUtils;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.login.AuthActivity;
import com.gumtree.android.login.registration.views.PasswordStrengthTextView;
import com.gumtree.android.login.resetpassword.di.DaggerResetPasswordComponent;
import com.gumtree.android.login.resetpassword.di.ResetPasswordComponent;
import com.gumtree.android.login.resetpassword.di.ResetPasswordModule;
import javax.inject.Inject;

public class ResetPasswordActivity extends BaseActivity implements View {
    @Bind({2131624222})
    EditText newPassword;
    @Bind({2131624221})
    TextInputLayout passwordLayout;
    @Bind({2131624224})
    TextView passwordPrompt;
    @Inject
    ResetPasswordPresenter presenter;
    @Bind({2131624223})
    PasswordStrengthTextView strengthIndicator;

    public static Intent createIntent(String str, String str2, Context context) {
        Intent intent = new Intent(context, ResetPasswordActivity.class);
        intent.putExtra("email", str);
        intent.putExtra("token", str2);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        getResetPasswordComponent().inject(this);
        setContentView(2130903106);
        ButterKnife.bind(this);
    }

    public ResetPasswordComponent getResetPasswordComponent() {
        String stringExtra = getIntent().getStringExtra("email");
        String stringExtra2 = getIntent().getStringExtra("token");
        ResetPasswordComponent resetPasswordComponent = (ResetPasswordComponent) ComponentsManager.get().getBaseComponent(ResetPasswordComponent.KEY);
        if (resetPasswordComponent != null) {
            return resetPasswordComponent;
        }
        Object build = DaggerResetPasswordComponent.builder().applicationComponent(ComponentsManager.get().getAppComponent()).resetPasswordModule(new ResetPasswordModule(stringExtra, stringExtra2)).build();
        ComponentsManager.get().putBaseComponent(ResetPasswordComponent.KEY, build);
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
            ComponentsManager.get().removeBaseComponent(ResetPasswordComponent.KEY);
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
                Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, parentActivityIntent) || isTaskRoot()) {
                    TaskStackBuilder.create(this).addNextIntentWithParentStack(parentActivityIntent).startActivities();
                } else {
                    NavUtils.navigateUpTo(this, parentActivityIntent);
                }
                return super.onOptionsItemSelected(menuItem);
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        hideKeyboard();
        super.onBackPressed();
    }

    @OnTextChanged({2131624222})
    void onPasswordChanged() {
        this.presenter.onPasswordChanged(this.newPassword.getText().toString());
    }

    @OnClick({2131624225})
    public void onSubmitNewPasswordClick() {
        this.presenter.resetPassword(this.newPassword.getText().toString());
    }

    @OnEditorAction({2131624222})
    protected boolean passwordEditorAction(int i) {
        if (i != 6) {
            return false;
        }
        onSubmitNewPasswordClick();
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

    public void showLoginFragment() {
        Intent createIntent = AuthActivity.createIntent(AuthIdentifier.RESET_PASSWORD, NavView.LOGIN, this);
        createIntent.setFlags(603979776);
        startActivity(createIntent);
        finish();
    }

    public void showPasswordError() {
        this.passwordLayout.setError(" ");
        this.passwordPrompt.setTextColor(ThemeUtils.getColor(this, 2130772480));
    }

    public void hidePasswordError() {
        this.passwordLayout.setError(null);
        this.passwordPrompt.setTextColor(ThemeUtils.getColor(this, 16842808));
    }

    public void showPasswordStrength(PasswordStrengthService$PasswordStrength passwordStrengthService$PasswordStrength) {
        this.strengthIndicator.update(passwordStrengthService$PasswordStrength);
    }
}
