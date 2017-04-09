package com.gumtree.android.vip.bing;

import com.gumtree.android.api.bing.BingAdsImpressionRequest;
import com.gumtree.android.srp.bing.BingIntentService.Operation;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingVIPIntentService$$Lambda$2 implements Operation {
    private final BingVIPIntentService arg$1;
    private final BingAdsImpressionRequest arg$2;

    private BingVIPIntentService$$Lambda$2(BingVIPIntentService bingVIPIntentService, BingAdsImpressionRequest bingAdsImpressionRequest) {
        this.arg$1 = bingVIPIntentService;
        this.arg$2 = bingAdsImpressionRequest;
    }

    public static Operation lambdaFactory$(BingVIPIntentService bingVIPIntentService, BingAdsImpressionRequest bingAdsImpressionRequest) {
        return new BingVIPIntentService$$Lambda$2(bingVIPIntentService, bingAdsImpressionRequest);
    }

    @Hidden
    public boolean execute() {
        return this.arg$1.lambda$handleImpressions$1(this.arg$2);
    }
}
