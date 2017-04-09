package com.gumtree.android.srp.bing;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingSRPCacheServiceBinder$$Lambda$1 implements Runnable {
    private static final BingSRPCacheServiceBinder$$Lambda$1 instance = new BingSRPCacheServiceBinder$$Lambda$1();

    private BingSRPCacheServiceBinder$$Lambda$1() {
    }

    public static Runnable lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void run() {
        BingSRPIntentService.sendImpressions();
    }
}
