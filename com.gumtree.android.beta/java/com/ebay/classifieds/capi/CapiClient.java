package com.ebay.classifieds.capi;

import android.support.annotation.NonNull;
import com.squareup.okhttp.Authenticator;
import com.squareup.okhttp.OkHttpClient;
import java.util.concurrent.TimeUnit;
import retrofit.RestAdapter;
import retrofit.RestAdapter.Builder;
import retrofit.RestAdapter.LogLevel;
import retrofit.client.OkClient;
import retrofit.converter.Converter;

public class CapiClient implements ICapiClient {
    private static final long CONNECTION_TIMEOUT = 30;
    private static final long READ_TIMEOUT = 30;
    private static final long WRITE_TIMEOUT = 30;
    private final RestAdapter restAdapter;

    public CapiClient(Converter converter, Authenticator authenticator, CapiConfig capiConfig) {
        this.restAdapter = new Builder().setEndpoint(capiConfig.getBaseUrl()).setRequestInterceptor(new CapiRequestInterceptor(capiConfig)).setConverter(converter).setErrorHandler(new CapiErrorHandler(capiConfig.getLocalisedTextProvider())).setLogLevel(capiConfig.isDebug() ? LogLevel.FULL : LogLevel.NONE).setClient(getOkClient(authenticator, capiConfig)).build();
    }

    @NonNull
    private OkClient getOkClient(Authenticator authenticator, CapiConfig capiConfig) {
        OkHttpClient okHttpClient = capiConfig.getOkHttpClient();
        okHttpClient.setConnectTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS);
        if (authenticator != null) {
            okHttpClient.setAuthenticator(authenticator);
        }
        return new OkClient(okHttpClient);
    }

    public <T> T api(Class<T> cls) {
        return this.restAdapter.create(cls);
    }
}
