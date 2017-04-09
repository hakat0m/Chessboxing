package com.gumtree.android.auth;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SyncService extends Service {
    private static final Object SYNC_ADAPTER_LOCK = new Object();
    private static SyncAdapter syncAdapter;

    public void onCreate() {
        synchronized (SYNC_ADAPTER_LOCK) {
            if (syncAdapter == null) {
                syncAdapter = new SyncAdapter(getApplicationContext(), true);
            }
        }
    }

    public IBinder onBind(Intent intent) {
        return syncAdapter.getSyncAdapterBinder();
    }
}
