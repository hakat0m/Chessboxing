package com.gumtree.android.srp.bing;

import android.content.Context;
import android.os.Handler;
import com.gumtree.android.srp.bing.BingSRPCache.CacheEventListener;

public class BingSRPCacheServiceBinder implements CacheEventListener {
    private static Handler handler = new Handler();
    private final boolean enableSRPPagination;
    private Runnable sync = BingSRPCacheServiceBinder$$Lambda$1.lambdaFactory$();
    private final int timeout;

    public BingSRPCacheServiceBinder(Context context) {
        this.enableSRPPagination = context.getResources().getBoolean(2131427348);
        this.timeout = context.getResources().getInteger(2131296291);
    }

    public void missed() {
        if (this.enableSRPPagination) {
            BingSRPIntentService.fetchNextPage();
        }
    }

    public void pageRead() {
        BingSRPIntentService.sendImpressions();
    }

    public void added(boolean z) {
        if (this.enableSRPPagination || z) {
            handler.removeCallbacks(this.sync);
            handler.postDelayed(this.sync, (long) this.timeout);
        }
    }
}
