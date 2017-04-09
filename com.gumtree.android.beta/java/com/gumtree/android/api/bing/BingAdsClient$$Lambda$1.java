package com.gumtree.android.api.bing;

import com.ebay.classifieds.capi.CapiConfig;
import java.lang.invoke.LambdaForm.Hidden;
import retrofit.RequestInterceptor;
import retrofit.RequestInterceptor.RequestFacade;

final /* synthetic */ class BingAdsClient$$Lambda$1 implements RequestInterceptor {
    private final CapiConfig arg$1;

    private BingAdsClient$$Lambda$1(CapiConfig capiConfig) {
        this.arg$1 = capiConfig;
    }

    public static RequestInterceptor lambdaFactory$(CapiConfig capiConfig) {
        return new BingAdsClient$$Lambda$1(capiConfig);
    }

    @Hidden
    public void intercept(RequestFacade requestFacade) {
        requestFacade.addHeader("User-Agent", this.arg$1.getWebUserAgent());
    }
}
