package com.gumtree.android.auth;

import android.accounts.Account;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;
import com.gumtree.android.savedsearches.SavedSearchSync;
import com.gumtree.android.startup.SecurityProviderManager;

public class SyncAdapter extends AbstractThreadedSyncAdapter {
    public static final String ACTION_FAILURE_SYNC = "com.gumtree.android.beta.ACTION_FAILURE_SYNC";
    public static final String ACTION_FINISHED_SYNC = "com.gumtree.android.beta.ACTION_FINISHED_SYNC";
    public static final String ACTION_LIMIT_REACHED_SYNC = "com.gumtree.android.beta.ACTION_LIMIT_REACHED_SYNC";
    public static final String ACTION_NETWORK_ERROR_SYNC = "com.gumtree.android.beta.ACTION_NETWORK_ERROR_SYNC";

    public SyncAdapter(Context context, boolean z) {
        super(context, z);
    }

    public SyncAdapter(Context context, boolean z, boolean z2) {
        super(context, z, z2);
    }

    public void onPerformSync(Account account, Bundle bundle, String str, ContentProviderClient contentProviderClient, SyncResult syncResult) {
        SecurityProviderManager.get().updateSecurityProviderSync(getContext(), syncResult);
        SavedSearchSync.executeSavedSearchesSync(getContext());
    }

    public void onSyncCanceled() {
        super.onSyncCanceled();
    }

    public void onSyncCanceled(Thread thread) {
        super.onSyncCanceled(thread);
    }
}
