package com.gumtree.android.srp.bing;

import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.srp.bing.BingIntentService.Operation;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingSRPIntentService$$Lambda$3 implements Operation {
    private final BingSRPIntentService arg$1;
    private final BingAdsImpressionRequest arg$2;

    private BingSRPIntentService$$Lambda$3(BingSRPIntentService bingSRPIntentService, BingAdsImpressionRequest bingAdsImpressionRequest) {
        this.arg$1 = bingSRPIntentService;
        this.arg$2 = bingAdsImpressionRequest;
    }

    public static Operation lambdaFactory$(BingSRPIntentService bingSRPIntentService, BingAdsImpressionRequest bingAdsImpressionRequest) {
        return new BingSRPIntentService$$Lambda$3(bingSRPIntentService, bingAdsImpressionRequest);
    }

    @Hidden
    public boolean execute() {
        return this.arg$1.lambda$handleImpressions$0(this.arg$2);
    }
}
