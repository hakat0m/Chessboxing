package com.gumtree.android.vip.bing;

import com.gumtree.android.srp.bing.BingIntentService.Operation;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingVIPIntentService$$Lambda$1 implements Operation {
    private final BingVIPIntentService arg$1;
    private final String arg$2;
    private final String arg$3;
    private final String arg$4;

    private BingVIPIntentService$$Lambda$1(BingVIPIntentService bingVIPIntentService, String str, String str2, String str3) {
        this.arg$1 = bingVIPIntentService;
        this.arg$2 = str;
        this.arg$3 = str2;
        this.arg$4 = str3;
    }

    public static Operation lambdaFactory$(BingVIPIntentService bingVIPIntentService, String str, String str2, String str3) {
        return new BingVIPIntentService$$Lambda$1(bingVIPIntentService, str, str2, str3);
    }

    @Hidden
    public boolean execute() {
        return this.arg$1.lambda$handleFetch$0(this.arg$2, this.arg$3, this.arg$4);
    }
}
