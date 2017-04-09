package com.gumtree.android.api.bing;

import com.ebay.classifieds.capi.CapiConfig;
import com.squareup.okhttp.OkHttpClient;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;

public class BingAdsClient {
    private final RestAdapter restAdapter;

    public BingAdsClient(CapiConfig capiConfig, OkHttpClient okHttpClient) {
        Builder client = new Builder().setEndpoint(capiConfig.getBingEndPoint()).setLogLevel(capiConfig.isDebug() ? LogLevel.FULL : LogLevel.NONE).setClient(new OkClient(okHttpClient));
        client.setRequestInterceptor(BingAdsClient$$Lambda$1.lambdaFactory$(capiConfig));
        this.restAdapter = client.build();
    }

    public <T> T api(Class<T> cls) {
        return this.restAdapter.create(cls);
    }
}
