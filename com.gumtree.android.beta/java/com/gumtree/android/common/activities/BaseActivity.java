package com.gumtree.android.common.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.inputmethod.InputMethodManager;
import com.apptentive.android.sdk.Apptentive;
import com.comscore.analytics.comScore;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.common.utils.crashlytics.CrashlyticsHelper;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.events.UserAccountClearedEvent;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.login.AuthActivity;
import com.squareup.otto.Subscribe;
import javax.inject.Inject;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public abstract class BaseActivity extends AppCompatActivity implements StatefulActivity {
    private static final int AUTH_ACTIVITY_REQUEST_CODE = 1245;
    @Inject
    BaseAccountManager accountManager;
    @Inject
    EventBus eventBus;
    private Object eventBusReceiver;
    @Inject
    Network network;

    public void setContentView(int i) {
        super.setContentView(i);
        setupToolbar();
    }

    public void setContentView(View view) {
        super.setContentView(view);
        setupToolbar();
    }

    public void setContentView(View view, LayoutParams layoutParams) {
        super.setContentView(view, layoutParams);
        setupToolbar();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        GumtreeApplication.component().inject(this);
        this.eventBusReceiver = new Object() {
            @Subscribe
            public void onUserAccountClearedEvent(UserAccountClearedEvent userAccountClearedEvent) {
                BaseActivity.this.onUserAccountCleared();
            }
        };
    }

    protected void setupToolbar() {
        Toolbar toolbar = getToolbar();
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(isHomeAsUp());
            getSupportActionBar().setHomeButtonEnabled(isHomeButtonEnabled());
        }
    }

    protected boolean isHomeButtonEnabled() {
        return true;
    }

    protected boolean isHomeAsUp() {
        return false;
    }

    protected Toolbar getToolbar() {
        return (Toolbar) findViewById(2131624104);
    }

    protected void onResume() {
        super.onResume();
        CrashlyticsHelper.getInstance().logBreadcrumb(getActivityName());
        this.network.enableConnectivityChangeReceiver();
        this.eventBus.register(this.eventBusReceiver);
    }

    public void startActivity(Intent intent) {
        super.startActivity(intent);
    }

    protected void onPostResume() {
        super.onPostResume();
        try {
            comScore.onEnterForeground();
        } catch (Exception e) {
        }
    }

    protected void onPause() {
        super.onPause();
        this.eventBus.unregister(this.eventBusReceiver);
        this.network.disableConnectivityChangeReceiver();
        try {
            comScore.onExitForeground();
        } catch (Exception e) {
        }
    }

    protected void hideKeyboard() {
        ((InputMethodManager) getSystemService("input_method")).hideSoftInputFromWindow(getWindow().getDecorView().findViewById(16908290).getWindowToken(), 0);
    }

    protected boolean isFragmentBackstackEmpty() {
        return getSupportFragmentManager().getBackStackEntryCount() == 0;
    }

    public boolean onSearchRequested() {
        return false;
    }

    protected void onStart() {
        super.onStart();
        if (this.accountManager.isUserLoggedIn()) {
            Apptentive.setPersonEmail(this.accountManager.getUsername());
        }
    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    protected void onStop() {
        super.onStop();
    }

    private String getActivityName() {
        return getClass().getSimpleName();
    }

    public void showSnackBar(String str) {
        View findViewById = findViewById(16908290);
        if (findViewById != null) {
            Snackbar.make(findViewById, str, 0).show();
        }
    }

    protected void onUserAccountCleared() {
        if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
            TaskStackBuilder create = TaskStackBuilder.create(this);
            create.addParentStack(AuthActivity.class);
            create.addNextIntent(AuthActivity.createIntent(AuthIdentifier.LOGGED_OUT, this));
            try {
                create.getPendingIntent(AUTH_ACTIVITY_REQUEST_CODE, 134217728).send();
                return;
            } catch (Throwable e) {
                Timber.e(e);
                return;
            }
        }
        AuthenticatorActivity.startActivity((Activity) this, 1);
    }
}
