package com.gumtree.android.auth;

import android.accounts.AccountAuthenticatorResponse;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.app.TaskStackBuilder;
import android.support.v4.content.Loader;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.UnderlineSpan;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import com.ebay.classifieds.capi.executor.Result;
import com.ebay.classifieds.capi.users.login.LoginApi;
import com.ebay.classifieds.capi.users.login.UserLogin;
import com.gumtree.Log;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.api.executor.RequestLoader;
import com.gumtree.android.auth.api.ActivateAccountIntentService;
import com.gumtree.android.auth.api.AuthRequest;
import com.gumtree.android.auth.google.model.SmartLockSaveResult;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.contracts.HorizontalProgressDisplay;
import com.gumtree.android.common.providers.AppProvider;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.OnActivatedAccountEvent;
import com.gumtree.android.home.HomeActivity;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.notifications.NotificationType;
import com.gumtree.android.notifications.providers.GCMSettingsProvider;
import com.gumtree.android.notifications.providers.PushNotificationsProvider;
import com.gumtree.android.post_ad.summary.PostAdSummaryActivity;
import com.gumtree.android.terms.TermsDialog;
import com.gumtree.android.userprofile.services.UserService;
import com.squareup.otto.Subscribe;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;

public class AuthenticatorActivity extends BaseActivity implements LoaderCallbacks<Result<UserLogin>>, OnClickListener, OnEditorActionListener, AuthenticatorLoginAction, HorizontalProgressDisplay {
    public static final int ACTIVATE_USER = 7;
    private static final String CASE_NOT_SUPPORTED = "case not supported ";
    public static final String EXTRA_CALLER = "extra_caller";
    public static final int FAVOURITES = 5;
    public static final int MANAGE_AD_ACTIVITY = 2;
    public static final int MESSAGES = 6;
    public static final String NEXT_ACTIVITY_EXTRA = "post-ad-extra";
    public static final int POST_AD_ACTIVITY = 1;
    public static final int SAVED_SEARCHES = 4;
    public static final int SETTINGS_ACTIVITY = 3;
    @Inject
    BaseAccountManager accountManager;
    @Inject
    PushNotificationsProvider appNotificationsProvider;
    @Inject
    EventBus eventBus;
    private SpannableString forgotString;
    private boolean isProgressVisible;
    private AccountAuthenticatorResponse mAccountAuthenticatorResponse;
    private boolean mLoginBeginAlreadyTracked;
    private String mLoginProvider;
    private Bundle mResultBundle;
    private EditText passwordET;
    private SpannableString regsiterString;
    private boolean requestNewAccount;
    private MenuItem sendMenuItem;
    @Inject
    UserService userService;
    private String username;
    private AutoCompleteTextView usernameET;

    public static void startActivity(Activity activity, int i) {
        Intent intent = new Intent(GumtreeApplication.getContext(), AuthenticatorActivity.class);
        intent.putExtra(NEXT_ACTIVITY_EXTRA, i);
        activity.startActivityForResult(intent, LocationActivity.ACTIVITY_REQUEST_CODE);
    }

    public static void startActivity(Fragment fragment, int i) {
        Intent intent = new Intent(GumtreeApplication.getContext(), AuthenticatorActivity.class);
        intent.putExtra(NEXT_ACTIVITY_EXTRA, i);
        fragment.startActivityForResult(intent, LocationActivity.ACTIVITY_REQUEST_CODE);
    }

    public static void startActivity(PreferenceFragment preferenceFragment, int i) {
        Intent intent = new Intent(GumtreeApplication.getContext(), AuthenticatorActivity.class);
        intent.putExtra(NEXT_ACTIVITY_EXTRA, i);
        preferenceFragment.startActivityForResult(intent, LocationActivity.ACTIVITY_REQUEST_CODE);
    }

    public static Intent createIntent(Context context, int i, String str, String str2) {
        Intent intent = new Intent(context, AuthenticatorActivity.class);
        intent.putExtra(NEXT_ACTIVITY_EXTRA, i);
        intent.putExtra("com.gumtree.android.auth.extra_activate_email", str);
        intent.putExtra("com.gumtree.android.auth.extra_activate_key", str2);
        return intent;
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        getWindow().setFlags(8192, 8192);
        this.mAccountAuthenticatorResponse = (AccountAuthenticatorResponse) getIntent().getParcelableExtra("accountAuthenticatorResponse");
        if (this.mAccountAuthenticatorResponse != null) {
            this.mAccountAuthenticatorResponse.onRequestContinued();
        }
        if (bundle != null) {
            this.mLoginProvider = bundle.getString(BaseAccountManager.KEY_LOGIN_PROVIDER);
            this.mLoginBeginAlreadyTracked = true;
        } else {
            handleActivateUser();
            this.mLoginBeginAlreadyTracked = false;
        }
        initVariables(getIntent());
        initUi();
        initProgressLoader(bundle);
        setupOnFirstInstall(bundle);
    }

    private void handleActivateUser() {
        Object stringExtra = getIntent().getStringExtra("com.gumtree.android.auth.extra_activate_email");
        Object stringExtra2 = getIntent().getStringExtra("com.gumtree.android.auth.extra_activate_key");
        if (!TextUtils.isEmpty(stringExtra) && !TextUtils.isEmpty(stringExtra2)) {
            ActivateAccountIntentService.activate(stringExtra, stringExtra2);
        }
    }

    private void setupOnFirstInstall(Bundle bundle) {
        if (GumtreeApplication.isFirstInstall(getApplicationContext()) && bundle == null) {
            TermsDialog.newInstance().show(getSupportFragmentManager(), "DIALOG");
        }
    }

    @Subscribe
    public void onActivatedAccountEvent(OnActivatedAccountEvent onActivatedAccountEvent) {
        if (onActivatedAccountEvent.isSuccess()) {
            Snackbar.make(getRootView(), 2131165346, 0).show();
            return;
        }
        CharSequence message = onActivatedAccountEvent.getResponseException().getError().getMessage();
        if (TextUtils.isEmpty(message)) {
            message = getString(2131165345);
        }
        Snackbar.make(getRootView(), message, 0).show();
    }

    private View getRootView() {
        return findViewById(2131624103);
    }

    protected void onStart() {
        super.onStart();
        this.eventBus.register(this);
        if (!this.mLoginBeginAlreadyTracked) {
            this.mLoginBeginAlreadyTracked = true;
            int intExtra = getIntent().getIntExtra(NEXT_ACTIVITY_EXTRA, 0);
            switch (intExtra) {
                case POST_AD_ACTIVITY /*1*/:
                    Track.eventLoginBeginFromPostAd();
                    return;
                case MANAGE_AD_ACTIVITY /*2*/:
                    Track.eventLoginBeginFromManageAds();
                    return;
                case SETTINGS_ACTIVITY /*3*/:
                    Track.eventLoginBeginFromSettings();
                    return;
                case SAVED_SEARCHES /*4*/:
                    Track.eventLoginBeginFromSavedSearches();
                    return;
                case FAVOURITES /*5*/:
                    Track.eventLoginBeginFromFavourites();
                    return;
                case MESSAGES /*6*/:
                    Track.eventLoginBeginFromMessages();
                    break;
                case ACTIVATE_USER /*7*/:
                    break;
            }
            Track.eventLoginBeginFromActivateLink();
            Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + intExtra);
        }
    }

    public void onStop() {
        this.eventBus.unregister(this);
        super.onStop();
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putBoolean(PostAdSummaryActivity.KEY_PROGRESS, this.isProgressVisible);
        bundle.putString(BaseAccountManager.KEY_LOGIN_PROVIDER, this.mLoginProvider);
        super.onSaveInstanceState(bundle);
    }

    protected void initProgressLoader(Bundle bundle) {
        if (bundle == null) {
            showProgress(false);
        } else {
            showProgress(bundle.getBoolean(PostAdSummaryActivity.KEY_PROGRESS));
        }
    }

    private void initVariables(Intent intent) {
        Object string = getString(2131165860);
        this.forgotString = new SpannableString(string);
        this.forgotString.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        string = getString(2131165889);
        this.regsiterString = new SpannableString(string);
        this.regsiterString.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        this.username = intent.getStringExtra("username");
        this.requestNewAccount = TextUtils.isEmpty(this.username);
    }

    private void initUi() {
        setContentView(2130903070);
        this.usernameET = (AutoCompleteTextView) findViewById(2131624108);
        this.usernameET.setAdapter(new ArrayAdapter(this, 2130903334, 16908308, CustomerAccountManager.getListOfUserNames(this)));
        this.usernameET.requestFocus();
        this.passwordET = (EditText) findViewById(2131624109);
        this.passwordET.setOnEditorActionListener(this);
        TextView textView = (TextView) findViewById(2131624110);
        textView.setText(this.regsiterString);
        TextView textView2 = (TextView) findViewById(2131624111);
        textView2.setText(this.forgotString);
        if (!TextUtils.isEmpty(this.username)) {
            this.usernameET.setText(this.username);
        }
        textView.setOnClickListener(this);
        textView2.setOnClickListener(this);
    }

    protected boolean isHomeAsUp() {
        return true;
    }

    public Loader<Result<UserLogin>> onCreateLoader(int i, Bundle bundle) {
        showProgress(true);
        return new RequestLoader(getApplicationContext(), new AuthRequest(bundle.getString(BaseAccountManager.KEY_USERNAME), bundle.getString(BaseAccountManager.KEY_PASSWORD), bundle.getString(BaseAccountManager.KEY_REL)));
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(2131755008, menu);
        this.sendMenuItem = menu.findItem(2131624929);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case 16908332:
                onUpPressed();
                break;
            case 2131624929:
                CharSequence password = getPassword();
                CharSequence username = getUsername();
                doLogin(username, password, null);
                if (!(TextUtils.isEmpty(password) || TextUtils.isEmpty(username))) {
                    Track.eventLoginAttempt(com.gumtree.android.common.utils.Log.TAG);
                    break;
                }
            default:
                return super.onOptionsItemSelected(menuItem);
        }
        return super.onOptionsItemSelected(menuItem);
    }

    protected void onUpPressed() {
        if (getIntent().getIntExtra(NEXT_ACTIVITY_EXTRA, 0) == SETTINGS_ACTIVITY) {
            finish();
            return;
        }
        TaskStackBuilder.from(this).addNextIntent(new Intent(this, HomeActivity.class)).startActivities();
        overridePendingTransition(0, 0);
    }

    public void onLoadFinished(Loader<Result<UserLogin>> loader, Result<UserLogin> result) {
        showProgress(false);
        if (result.hasError()) {
            onSocialLoginFailed();
            enableSocialLoginButtons();
            Track.eventLoginFail(this.mLoginProvider);
            FBHelper.logoutViaFacebook();
            resetGoogleApiState();
            Toast.makeText(getApplicationContext(), result.getError().getMessage(), 0).show();
            return;
        }
        finishLogin((UserLogin) result.getData());
        this.accountManager.initialiseSync();
        this.userService.update();
        Track.eventLoginSuccess(this.mLoginProvider);
    }

    private void resetGoogleApiState() {
        SocialLoginFragmentGoogle socialLoginFragmentGoogle = (SocialLoginFragmentGoogle) getSupportFragmentManager().findFragmentById(2131624106);
        if (socialLoginFragmentGoogle != null) {
            socialLoginFragmentGoogle.resetGoogleApiState();
        }
    }

    private void enableSocialLoginButtons() {
        findViewById(2131624464).setEnabled(true);
        findViewById(2131624863).setEnabled(true);
    }

    private void finishLogin(UserLogin userLogin) {
        this.accountManager.createAccount(this.requestNewAccount, userLogin);
        Intent intent = new Intent();
        intent.putExtra("authAccount", userLogin.getUserEmail());
        intent.putExtra("password", userLogin.getPassword());
        intent.putExtra("accountType", AppProvider.AUTHORITY);
        intent.putExtra(EXTRA_CALLER, getIntent().getIntExtra(NEXT_ACTIVITY_EXTRA, 0));
        setAccountAuthenticatorResult(intent.getExtras());
        setResult(-1, intent);
        Map hashMap = new HashMap();
        hashMap.put(NotificationType.SAVED_SEARCHES, Boolean.valueOf(true));
        hashMap.put(NotificationType.CHAT_MESSAGE, Boolean.valueOf(true));
        new GCMSettingsProvider().persistNotificationTypesStates(hashMap);
        this.appNotificationsProvider.updateSubscriptions(hashMap, null);
        hideKeyboard();
        finish();
    }

    public void onBackPressed() {
        Track.eventLoginCancel();
        super.onBackPressed();
    }

    public void onLoaderReset(Loader<Result<UserLogin>> loader) {
    }

    public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
        textView.getId();
        if (i == MESSAGES) {
            CharSequence password = getPassword();
            CharSequence username = getUsername();
            doLogin(username, password, null);
            if (!(TextUtils.isEmpty(password) || TextUtils.isEmpty(username))) {
                Track.eventLoginAttempt(com.gumtree.android.common.utils.Log.TAG);
            }
        }
        return false;
    }

    private void doLogin(String str, String str2, String str3) {
        if (TextUtils.isEmpty(str2) || TextUtils.isEmpty(str)) {
            Toast.makeText(getApplicationContext(), getString(2131165435), 0).show();
            return;
        }
        updateLoginProvider(str3);
        this.requestNewAccount = !str.equals(this.username);
        Bundle bundle = new Bundle();
        bundle.putString(BaseAccountManager.KEY_USERNAME, str);
        bundle.putString(BaseAccountManager.KEY_PASSWORD, str2);
        bundle.putString(BaseAccountManager.KEY_REL, str3);
        getSupportLoaderManager().restartLoader(0, bundle, this);
    }

    private void updateLoginProvider(String str) {
        if (LoginApi.REL_GOOGLE.equals(str)) {
            this.mLoginProvider = "Google";
        } else if (LoginApi.REL_FACEBOOK.equals(str)) {
            this.mLoginProvider = "Facebook";
        } else {
            this.mLoginProvider = com.gumtree.android.common.utils.Log.TAG;
        }
    }

    private String getUsername() {
        return this.usernameET.getText().toString().trim();
    }

    private String getPassword() {
        return this.passwordET.getText().toString();
    }

    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case 2131624110:
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(GumtreeApplication.getAPI().getRegistrationUrl()));
                startActivity(intent);
                return;
            case 2131624111:
                intent = new Intent("android.intent.action.VIEW");
                intent.setData(Uri.parse(GumtreeApplication.getAPI().getForgotPasswordUrl()));
                startActivity(intent);
                return;
            default:
                Log.e(getClass().getSimpleName(), CASE_NOT_SUPPORTED + view.getId());
                return;
        }
    }

    public final void setAccountAuthenticatorResult(Bundle bundle) {
        this.mResultBundle = bundle;
    }

    public void finish() {
        if (this.mAccountAuthenticatorResponse != null) {
            if (this.mResultBundle != null) {
                this.mAccountAuthenticatorResponse.onResult(this.mResultBundle);
            } else {
                this.mAccountAuthenticatorResponse.onError(SAVED_SEARCHES, SmartLockSaveResult.CANCELED);
            }
            this.mAccountAuthenticatorResponse = null;
        }
        super.finish();
    }

    public void showProgress(boolean z) {
        this.isProgressVisible = z;
        findViewById(2131624105).setVisibility(this.isProgressVisible ? 0 : 8);
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i == 0) {
            getSupportFragmentManager().findFragmentById(2131624106).onActivityResult(i, i2, intent);
        }
    }

    public void onSocialLoginStarted() {
        showProgress(true);
        if (this.sendMenuItem != null) {
            this.sendMenuItem.setVisible(false);
        }
        this.usernameET.setEnabled(false);
        this.passwordET.setEnabled(false);
    }

    public void onSocialLoginFailed() {
        showProgress(false);
        if (this.sendMenuItem != null) {
            this.sendMenuItem.setVisible(true);
        }
        this.usernameET.setEnabled(true);
        this.passwordET.setEnabled(true);
    }

    public void onSocialLoginSuccessful(String str, String str2, String str3) {
        doLogin(str, str2, str3);
    }
}
