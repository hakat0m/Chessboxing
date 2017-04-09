package com.gumtree.android.api.callisto;

import android.support.annotation.NonNull;
import com.gumtree.android.api.Retrofit2Client;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public final class CallistoClient implements Retrofit2Client {
    private final Retrofit restAdapter;

    public CallistoClient(@NonNull Factory factory, @NonNull CallAdapter.Factory factory2, @NonNull OkHttpClient okHttpClient, @NonNull CallistoConfig callistoConfig) {
        this.restAdapter = new Builder().baseUrl(callistoConfig.getBaseURL().toExternalForm()).addConverterFactory(factory).addCallAdapterFactory(factory2).client(okHttpClient).validateEagerly(true).build();
    }

    public <T> T api(Class<T> cls) {
        return this.restAdapter.create(cls);
    }
}
