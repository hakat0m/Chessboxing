package com.gumtree.android.api.treebay;

import android.support.annotation.NonNull;
import com.gumtree.android.api.Retrofit2Client;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter.Factory;
import retrofit2.Retrofit;
import retrofit2.Retrofit.Builder;

public class TreebayClient implements Retrofit2Client {
    private final Retrofit retrofit;

    public TreebayClient(@NonNull Factory factory, @NonNull CallAdapter.Factory factory2, @NonNull OkHttpClient okHttpClient) {
        this.retrofit = new Builder().baseUrl("https://treebay.ebayclassifiedsgroup.com/").addConverterFactory(factory).addCallAdapterFactory(factory2).client(okHttpClient).validateEagerly(true).build();
    }

    public <T> T api(Class<T> cls) {
        return this.retrofit.create(cls);
    }
}
