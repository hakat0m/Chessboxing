package com.gumtree.android.vip.bing;

import android.content.Context;
import android.os.Handler;
import com.gumtree.android.vip.bing.BingVIPCache.CacheEventListener;

public class BingVIPCacheServiceBinder implements CacheEventListener {
    private static Handler handler = new Handler();
    private Runnable sync = BingVIPCacheServiceBinder$$Lambda$1.lambdaFactory$();
    private final int timeout;

    public BingVIPCacheServiceBinder(Context context) {
        this.timeout = context.getResources().getInteger(2131296292);
    }

    public void added() {
        handler.removeCallbacks(this.sync);
        handler.postDelayed(this.sync, (long) this.timeout);
    }
}
