package com.gumtree.android.clients;

public interface SoftUpdateSettingsProvider {
    public static final int SHOW_COUNT_LIMIT = 3;

    String getAppId();

    int getShownCount();

    int getVersionCode();

    void incrementShownCount();

    boolean isDebug();

    boolean isFirstInstall();
}
