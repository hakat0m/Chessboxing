package com.gumtree.android.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.MenuItem;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.apptentive.android.sdk.BuildConfig;
import com.apptentive.android.sdk.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.IdToken;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter;
import com.gumtree.android.auth.google.model.SmartLockCredential;
import com.gumtree.android.auth.google.model.SmartLockSaveResult;
import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter;
import com.gumtree.android.auth.google.services.GoogleService;
import com.gumtree.android.auth.login.presenter.LoginPresenter;
import com.gumtree.android.auth.presenter.AuthPresenter;
import com.gumtree.android.auth.presenter.AuthPresenter.View;
import com.gumtree.android.auth.presenter.NavView;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.login.di.AuthComponent;
import com.gumtree.android.login.di.AuthModule;
import com.gumtree.android.login.google.GoogleLoginFragment;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.vip_treebay.VIPActivityTreebay;
import javax.inject.Inject;

public class AuthActivity extends BaseActivity implements View {
    public static final String TAG_GOOGLE_FRAGMENT = GoogleLoginFragment.class.getSimpleName();
    @Inject
    FacebookLoginPresenter facebookLoginPresenter;
    private GoogleLoginFragment googleFragment;
    @Inject
    GoogleLoginPresenter googleLoginPresenter;
    @Bind({2131624158})
    ViewPager loginPager;
    @Inject
    LoginPresenter loginPresenter;
    @Inject
    AuthPresenter presenter;
    @Inject
    GoogleService smartLockService;
    @Bind({2131624157})
    TabLayout tabs;

    public static Intent createIntent(AuthIdentifier authIdentifier, Context context) {
        return createIntent(authIdentifier, NavView.SPLASH, context);
    }

    public static Intent createIntent(AuthIdentifier authIdentifier, NavView navView, Context context) {
        Intent intent = new Intent(context, AuthActivity.class);
        intent.putExtra(AuthIdentifier.class.getSimpleName(), authIdentifier);
        intent.putExtra(NavView.class.getSimpleName(), navView);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        AuthModule.inject(this);
        setContentView(2130903088);
        ButterKnife.bind(this);
        this.googleFragment = new GoogleLoginFragment();
        this.loginPager.setAdapter(new AuthAdapter(this));
        this.loginPager.addOnPageChangeListener(new OnPageChangeListener() {
            public void onPageScrolled(int i, float f, int i2) {
            }

            public void onPageSelected(int i) {
                AuthActivity.this.presenter.navigateTo(AuthActivity.this.getNavViewFromPosition(i));
            }

            public void onPageScrollStateChanged(int i) {
            }
        });
        this.tabs.setupWithViewPager(this.loginPager);
    }

    public NavView getNavViewFromPosition(int i) {
        switch (i) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return NavView.LOGIN;
            case HighlightView.GROW_NONE /*1*/:
                return NavView.REGISTRATION;
            default:
                return NavView.SPLASH;
        }
    }

    protected void onStart() {
        super.onStart();
        this.presenter.attachView(this);
    }

    protected void onStop() {
        super.onStop();
        this.googleLoginPresenter.onResolutionDone(this);
        this.presenter.detachView();
    }

    protected void onDestroy() {
        if (isFinishing()) {
            this.presenter.destroy();
            this.googleLoginPresenter.destroy();
            this.facebookLoginPresenter.destroy();
            ComponentsManager.get().removeBaseComponent(AuthComponent.KEY);
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
                return true;
            default:
                return super.onOptionsItemSelected(menuItem);
        }
    }

    public void onBackPressed() {
        hideKeyboard();
        this.presenter.onBack(getSupportFragmentManager().getBackStackEntryCount());
    }

    public void showSplash(boolean z) {
        if (((GoogleLoginFragment) getSupportFragmentManager().findFragmentByTag(TAG_GOOGLE_FRAGMENT)) == null) {
            int i = z ? 17432576 : 0;
            getSupportFragmentManager().beginTransaction().addToBackStack(TAG_GOOGLE_FRAGMENT).setCustomAnimations(i, i, 2130968613, 2130968613).add(2131624159, this.googleFragment, TAG_GOOGLE_FRAGMENT).commit();
        }
    }

    public void showLogin() {
        getSupportFragmentManager().popBackStack();
        this.loginPager.setCurrentItem(NavView.LOGIN.getValue());
    }

    public void showRegistration() {
        getSupportFragmentManager().popBackStack();
        this.loginPager.setCurrentItem(NavView.REGISTRATION.getValue());
    }

    public void showTabs() {
        this.loginPager.setVisibility(0);
        this.tabs.setVisibility(0);
    }

    public void hideTabs() {
        this.loginPager.setVisibility(8);
        this.tabs.setVisibility(8);
    }

    public void showPreviousActivity() {
        finish();
    }

    public void showToolbarTitle(String str) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(str);
        }
    }

    public void showUserActivationMessage(String str) {
        showSnackBar(str);
    }

    public void showHomeActivity() {
        Intent parentActivityIntent = NavUtils.getParentActivityIntent(this);
        if (NavUtils.shouldUpRecreateTask(this, parentActivityIntent) || isTaskRoot()) {
            TaskStackBuilder.create(this).addNextIntentWithParentStack(parentActivityIntent).startActivities();
        } else {
            NavUtils.navigateUpTo(this, parentActivityIntent);
        }
        finish();
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        switch (i) {
            case R.styleable.AppCompatTheme_controlBackground /*91*/:
                this.googleLoginPresenter.onResolutionDone(this);
                switch (i2) {
                    case VIPActivityTreebay.INVALID_VIP_ID /*-1*/:
                        this.loginPresenter.onSmartLockSaveResolved(SmartLockSaveResult.SUCCESS);
                        return;
                    case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                        this.loginPresenter.onSmartLockSaveResolved(SmartLockSaveResult.CANCELED);
                        return;
                    default:
                        this.loginPresenter.onSmartLockSaveResolved(SmartLockSaveResult.FAILURE);
                        return;
                }
            case 9001:
                GoogleSignInResult signInResultFromIntent = Auth.GoogleSignInApi.getSignInResultFromIntent(intent);
                String str = BuildConfig.FLAVOR;
                String str2 = BuildConfig.FLAVOR;
                if (signInResultFromIntent.isSuccess() && signInResultFromIntent.getSignInAccount() != null) {
                    str = signInResultFromIntent.getSignInAccount().getEmail();
                    str2 = signInResultFromIntent.getSignInAccount().getIdToken();
                }
                this.googleLoginPresenter.onGoogleResult(str, str2);
                return;
            case 9111:
                this.googleLoginPresenter.onResolutionDone(this);
                if (i2 == -1) {
                    Credential credential = (Credential) intent.getParcelableExtra("com.google.android.gms.credentials.Credential");
                    this.googleLoginPresenter.fillRegistrationFormWithHint(this, new SmartLockCredential(credential.getId(), credential.getName(), credential.getIdTokens().isEmpty() ? null : ((IdToken) credential.getIdTokens().get(0)).getIdToken(), false));
                    return;
                }
                return;
            default:
                return;
        }
    }
}
