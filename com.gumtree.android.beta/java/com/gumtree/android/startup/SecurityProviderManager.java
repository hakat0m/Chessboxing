package com.gumtree.android.startup;

import android.content.Context;
import android.content.Intent;
import android.content.SyncResult;
import android.content.SyncStats;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.google.android.gms.security.ProviderInstaller.ProviderInstallListener;

public class SecurityProviderManager implements ProviderInstallListener {
    private SecurityProviderManager() {
    }

    public static SecurityProviderManager get() {
        return LazyHolder.access$000();
    }

    public void updateSecurityProviderSync(Context context, SyncResult syncResult) {
        SyncStats syncStats;
        try {
            ProviderInstaller.installIfNeeded(context);
        } catch (GooglePlayServicesRepairableException e) {
            syncStats = syncResult.stats;
            syncStats.numIoExceptions++;
        } catch (GooglePlayServicesNotAvailableException e2) {
            syncStats = syncResult.stats;
            syncStats.numAuthExceptions++;
        }
    }

    public void updateSecurityProviderAsync(Context context) {
        ProviderInstaller.installIfNeededAsync(context, this);
    }

    public void onProviderInstalled() {
    }

    public void onProviderInstallFailed(int i, Intent intent) {
    }
}
