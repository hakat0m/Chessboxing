package com.gumtree.android.savedsearches;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.AbundanceActivity;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.analytics.Track;
import com.gumtree.android.common.drawer.NavigationItem;
import com.gumtree.android.dagger.ComponentsManager;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;
import javax.inject.Inject;

public class SavedSearchesActivity extends NavigationActivity implements AbundanceActivity {
    public static final String EXTRA_FROM_PUSH_NOTIFICATION = "com.gumtree.android.savedsearches.fromPushNotification";
    @Inject
    BaseAccountManager accountManager;
    private boolean mTrackingFromNotification;

    public static Intent createIntent(Context context) {
        return new Intent(context, SavedSearchesActivity.class);
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(2130903107, bundle);
        ComponentsManager.get().getAppComponent().inject(this);
        initState(bundle);
        if (bundle == null) {
            this.mTrackingFromNotification = getIntent().getBooleanExtra(EXTRA_FROM_PUSH_NOTIFICATION, false);
        }
    }

    private void initState(Bundle bundle) {
        if (!this.accountManager.isUserLoggedIn() && bundle == null) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SAVED_SEARCHES, this), LocationActivity.ACTIVITY_REQUEST_CODE);
            } else {
                AuthenticatorActivity.startActivity((Activity) this, 4);
            }
        }
        if (bundle == null) {
            refreshView();
        }
    }

    protected void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        if (i != LocationActivity.ACTIVITY_REQUEST_CODE) {
            return;
        }
        if (i2 == -1) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled() && intent.hasExtra("displayname")) {
                showSnackBar(getString(2131165552, new Object[]{intent.getStringExtra("displayname")}));
            }
            initState(null);
            return;
        }
        finish();
    }

    protected NavigationItem getNavigationItem() {
        return NavigationItem.SAVED_SEARCHES;
    }

    protected String getTrackingView() {
        return "SavedSearches";
    }

    protected void onStart() {
        super.onStart();
        if (this.mTrackingFromNotification) {
            Track.eventSavedSearchNotificationOpen();
            this.mTrackingFromNotification = false;
        }
        Track.viewSavedSearches();
    }

    public void onStop() {
        super.onStop();
    }

    protected void onDestroy() {
        NewSavedSearchesIntentService.clearNewSavedSearch();
        super.onDestroy();
    }

    public void refreshView() {
        SavedSearchesFragment savedSearchesFragment = (SavedSearchesFragment) getSupportFragmentManager().findFragmentById(2131624130);
        if (savedSearchesFragment != null) {
            savedSearchesFragment.syncSavedSearches();
        }
    }

    public void setAbundanceInTitle(String str) {
        getSupportActionBar().setSubtitle(Html.fromHtml(str));
    }
}
