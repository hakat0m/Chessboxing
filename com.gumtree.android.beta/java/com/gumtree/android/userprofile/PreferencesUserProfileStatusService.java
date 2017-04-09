package com.gumtree.android.userprofile;

import android.content.Context;
import android.content.SharedPreferences.Editor;

public class PreferencesUserProfileStatusService implements UserProfileStatusService {
    private static String USER_PROFILE_PREFERENCES_KEY = "USER_PROFILE_PREFERENCES";
    private static String USER_PROFILE_UPDATED = "USER_PROFILE_UPDATED";
    private final Context context;

    public PreferencesUserProfileStatusService(Context context) {
        this.context = context;
    }

    public boolean isProfileDirty() {
        if (this.context.getSharedPreferences(USER_PROFILE_PREFERENCES_KEY, 0).getBoolean(USER_PROFILE_UPDATED, false)) {
            return false;
        }
        return true;
    }

    public void setProfileDirty(boolean z) {
        boolean z2 = false;
        Editor edit = this.context.getSharedPreferences(USER_PROFILE_PREFERENCES_KEY, 0).edit();
        String str = USER_PROFILE_UPDATED;
        if (!z) {
            z2 = true;
        }
        edit.putBoolean(str, z2).apply();
    }
}
