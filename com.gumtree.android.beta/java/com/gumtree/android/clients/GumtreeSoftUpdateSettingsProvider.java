package com.gumtree.android.clients;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.WorkerThread;
import com.gumtree.android.GumtreeApplication;
import com.gumtree.android.common.providers.AppProvider;

public class GumtreeSoftUpdateSettingsProvider implements SoftUpdateSettingsProvider {
    private final Context context;

    public GumtreeSoftUpdateSettingsProvider(Context context) {
        this.context = context.getApplicationContext();
    }

    public int getVersionCode() {
        return 312;
    }

    public String getAppId() {
        return AppProvider.AUTHORITY;
    }

    @WorkerThread
    public int getShownCount() {
        return PreferenceManager.getDefaultSharedPreferences(this.context).getInt(getClass().getSimpleName(), 0);
    }

    @WorkerThread
    public void incrementShownCount() {
        PreferenceManager.getDefaultSharedPreferences(this.context).edit().putInt(getClass().getSimpleName(), getShownCount() + 1).apply();
    }

    @WorkerThread
    public boolean isFirstInstall() {
        return GumtreeApplication.isFirstInstall(this.context);
    }

    public boolean isDebug() {
        return false;
    }
}
