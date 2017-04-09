package com.gumtree.android.common.cleanup;

import android.content.Context;
import com.gumtree.android.model.ServiceCallRegistry;

public class ServiceCallRegistryCleanup implements Cleanup {
    private static final long WEEK = 604800000;
    private Context context;

    public ServiceCallRegistryCleanup(Context context) {
        this.context = context;
    }

    public void startCleanup() {
        this.context.getContentResolver().delete(ServiceCallRegistry.URI, "inserted_at < " + (System.currentTimeMillis() - WEEK), null);
    }
}
