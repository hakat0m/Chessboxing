package com.gumtree.android.common.fragments;

import android.app.Activity;
import com.gumtree.DevelopmentFlags;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.auth.AuthBaseStrategy;
import com.gumtree.android.auth.AuthIdentifier;
import com.gumtree.android.auth.AuthenticatorActivity;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.utils.Log;
import com.gumtree.android.locations.LocationActivity;
import com.gumtree.android.login.AuthActivity;

public class GumtreeAuthBaseStrategy implements AuthBaseStrategy {
    private final ApplicationDataManager applicationDataManager;
    private final BaseAccountManager user;

    public GumtreeAuthBaseStrategy(BaseAccountManager baseAccountManager, ApplicationDataManager applicationDataManager) {
        this.user = baseAccountManager;
        this.applicationDataManager = applicationDataManager;
    }

    public boolean renewToken(int i, Activity activity) {
        if (401 != i) {
            return false;
        }
        this.applicationDataManager.clearAllUserData();
        try {
            this.user.removeAccount(null);
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                activity.startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SETTINGS, activity), LocationActivity.ACTIVITY_REQUEST_CODE);
                return true;
            }
            AuthenticatorActivity.startActivity(activity, 1);
            return true;
        } catch (Exception e) {
            Log.v(Log.TAG, e.getMessage());
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                activity.startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SETTINGS, activity), LocationActivity.ACTIVITY_REQUEST_CODE);
                return true;
            }
            AuthenticatorActivity.startActivity(activity, 1);
            return true;
        } catch (Throwable th) {
            if (DevelopmentFlags.getInstance().isNewLoginEnabled()) {
                activity.startActivityForResult(AuthActivity.createIntent(AuthIdentifier.SETTINGS, activity), LocationActivity.ACTIVITY_REQUEST_CODE);
                return true;
            }
            AuthenticatorActivity.startActivity(activity, 1);
            return true;
        }
    }
}
