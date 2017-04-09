package com.gumtree.android.dagger.modules;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import com.ebay.classifieds.capi.CapiClient;
import com.ebay.classifieds.capi.CapiConfig;
import com.ebay.classifieds.capi.ContentConverters;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.PicturesUploadCapiClient;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.gumtree.android.api.ApiConfig;
import com.gumtree.android.api.CallistoApiConfig;
import com.gumtree.android.api.EnvironmentSettings;
import com.gumtree.android.api.Retrofit2Client;
import com.gumtree.android.api.bing.BingAdsClient;
import com.gumtree.android.api.callisto.CallistoClient;
import com.gumtree.android.api.callisto.CallistoConfig;
import com.gumtree.android.api.callisto.RxCallistoErrorHandlingCallAdapterFactory;
import com.gumtree.android.api.treebay.TreebayClient;
import com.gumtree.android.auth.ApiAuthenticator;
import com.gumtree.android.auth.ApplicationDataManager;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.http.CAPIIntentFactory;
import com.gumtree.android.common.http.GumtreeCAPIIntentFactory;
import com.gumtree.android.dagger.scope.ApplicationScope;
import com.squareup.okhttp.Authenticator;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import okhttp3.OkHttpClient;
import retrofit2.CallAdapter;
import retrofit2.Converter.Factory;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkClientModule {
    @Provides
    @ApplicationScope
    public EnvironmentSettings provideEnvironmentSettings(Resources resources, SharedPreferences sharedPreferences) {
        return new EnvironmentSettings(resources, sharedPreferences);
    }

    @Provides
    @ApplicationScope
    public Factory provideConverterFactory(ObjectMapper objectMapper) {
        return JacksonConverterFactory.create(objectMapper);
    }

    @Provides
    @Named("rxCallAdapter")
    @ApplicationScope
    public CallAdapter.Factory provideCallAdapterFactory() {
        return RxJavaCallAdapterFactory.create();
    }

    @Provides
    @Named("rxCallistoCallAdapter")
    @ApplicationScope
    public CallAdapter.Factory provideCallistoCallAdapterFactory(@Named("rxCallAdapter") CallAdapter.Factory factory) {
        return RxCallistoErrorHandlingCallAdapterFactory.create(factory);
    }

    @Provides
    @Named("treebayClient")
    @ApplicationScope
    public Retrofit2Client provideTreebayClient(Factory factory, @Named("rxCallAdapter") CallAdapter.Factory factory2, OkHttpClient okHttpClient) {
        return new TreebayClient(factory, factory2, okHttpClient);
    }

    @Provides
    @ApplicationScope
    public CallistoConfig providesCallistoApiConfig(EnvironmentSettings environmentSettings) {
        return new CallistoApiConfig(environmentSettings);
    }

    @Provides
    @Named("callistoClient")
    @ApplicationScope
    public Retrofit2Client provideCallistoClient(Factory factory, @Named("rxCallistoCallAdapter") CallAdapter.Factory factory2, OkHttpClient okHttpClient, CallistoConfig callistoConfig) {
        return new CallistoClient(factory, factory2, okHttpClient, callistoConfig);
    }

    @Provides
    @ApplicationScope
    public Authenticator provideApiAuthenticator(ApplicationDataManager applicationDataManager) {
        return new ApiAuthenticator(applicationDataManager);
    }

    @Provides
    @ApplicationScope
    public CapiConfig providesApiConfig(Context context, com.squareup.okhttp.OkHttpClient okHttpClient, BaseAccountManager baseAccountManager, EnvironmentSettings environmentSettings) {
        return new ApiConfig(context, okHttpClient, baseAccountManager, environmentSettings);
    }

    @Provides
    @Named("xmlClient")
    @ApplicationScope
    public ICapiClient provideXmlCapiClient(Authenticator authenticator, CapiConfig capiConfig) {
        return new CapiClient(ContentConverters.getXMLConverter(), authenticator, capiConfig);
    }

    @Provides
    @Named("gsonClient")
    @ApplicationScope
    public ICapiClient provideGsonCapiClient(Authenticator authenticator, CapiConfig capiConfig) {
        return new CapiClient(ContentConverters.getGsonConverter(), authenticator, capiConfig);
    }

    @Provides
    @Named("picturesUploadClient")
    @ApplicationScope
    public ICapiClient providePicturesUploadCapiClient(Authenticator authenticator, CapiConfig capiConfig) {
        return new PicturesUploadCapiClient(ContentConverters.getXMLConverter(), authenticator, capiConfig);
    }

    @Provides
    @ApplicationScope
    public CAPIIntentFactory provideCAPIIntentFactory(EnvironmentSettings environmentSettings) {
        return new GumtreeCAPIIntentFactory(environmentSettings);
    }

    @Provides
    @ApplicationScope
    public BingAdsClient provideBingAdsClient(CapiConfig capiConfig, com.squareup.okhttp.OkHttpClient okHttpClient) {
        return new BingAdsClient(capiConfig, okHttpClient);
    }
}
