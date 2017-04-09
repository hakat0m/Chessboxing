package com.gumtree.android.srp.bing;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingAdView$$Lambda$2 implements Runnable {
    private final BingAdView arg$1;

    private BingAdView$$Lambda$2(BingAdView bingAdView) {
        this.arg$1 = bingAdView;
    }

    public static Runnable lambdaFactory$(BingAdView bingAdView) {
        return new BingAdView$$Lambda$2(bingAdView);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$new$0();
    }
}
