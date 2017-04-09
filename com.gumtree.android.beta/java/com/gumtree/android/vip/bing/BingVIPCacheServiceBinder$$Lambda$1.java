package com.gumtree.android.vip.bing;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingVIPCacheServiceBinder$$Lambda$1 implements Runnable {
    private static final BingVIPCacheServiceBinder$$Lambda$1 instance = new BingVIPCacheServiceBinder$$Lambda$1();

    private BingVIPCacheServiceBinder$$Lambda$1() {
    }

    public static Runnable lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void run() {
        BingVIPIntentService.sendImpressions();
    }
}
