package com.gumtree.android.srp.bing;

import com.gumtree.android.srp.bing.BingIntentService.Operation;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingSRPIntentService$$Lambda$2 implements Operation {
    private final BingSRPIntentService arg$1;

    private BingSRPIntentService$$Lambda$2(BingSRPIntentService bingSRPIntentService) {
        this.arg$1 = bingSRPIntentService;
    }

    public static Operation lambdaFactory$(BingSRPIntentService bingSRPIntentService) {
        return new BingSRPIntentService$$Lambda$2(bingSRPIntentService);
    }

    @Hidden
    public boolean execute() {
        return this.arg$1.fetchResult();
    }
}
