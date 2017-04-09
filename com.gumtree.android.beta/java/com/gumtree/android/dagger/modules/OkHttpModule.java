package com.gumtree.android.dagger.modules;

import android.annotation.SuppressLint;
import com.facebook.stetho.okhttp.StethoInterceptor;
import com.gumtree.android.api.okhttp.DefaultOkHttpConfig;
import com.gumtree.android.api.okhttp.OkHttpConfig;
import com.gumtree.android.dagger.scope.ApplicationScope;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;
import javax.inject.Named;
import javax.inject.Provider;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import okhttp3.OkHttpClient.Builder;
import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.logging.HttpLoggingInterceptor.Level;
import retrofit.client.Client;
import retrofit.client.OkClient;

@Module
public class OkHttpModule {
    @Provides
    @ApplicationScope
    public OkHttpConfig providesOkHttpConfig() {
        return new DefaultOkHttpConfig();
    }

    @Provides
    @ApplicationScope
    public X509TrustManager providesX509TrustManager() {
        return new X509TrustManager() {
            @SuppressLint({"TrustAllX509TrustManager"})
            public void checkClientTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            @SuppressLint({"TrustAllX509TrustManager"})
            public void checkServerTrusted(X509Certificate[] x509CertificateArr, String str) throws CertificateException {
            }

            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    @Provides
    @ApplicationScope
    public SSLSocketFactory provideSslSocketFactory(X509TrustManager x509TrustManager) {
        Throwable e;
        try {
            TrustManager[] trustManagerArr = new TrustManager[]{x509TrustManager};
            SSLContext instance = SSLContext.getInstance("SSL");
            instance.init(null, trustManagerArr, new SecureRandom());
            return instance.getSocketFactory();
        } catch (NoSuchAlgorithmException e2) {
            e = e2;
            throw new RuntimeException(e);
        } catch (KeyManagementException e3) {
            e = e3;
            throw new RuntimeException(e);
        }
    }

    private boolean acceptHost(String str) {
        return str.contains("gumtree.com") || str.contains(".qa.gt.ecg.so") || str.contains("treebay.ebayclassifiedsgroup.com");
    }

    @Provides
    @Named("debugOkHttp2Client")
    @ApplicationScope
    public OkHttpClient provideDebugOkHttp2Client(SSLSocketFactory sSLSocketFactory, OkHttpConfig okHttpConfig) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(okHttpConfig.getConnectTimeout(), TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(okHttpConfig.getReadTimeout(), TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(okHttpConfig.getWriteTimeout(), TimeUnit.SECONDS);
        okHttpClient.setSslSocketFactory(sSLSocketFactory);
        okHttpClient.setHostnameVerifier(OkHttpModule$$Lambda$1.lambdaFactory$(this));
        okHttpClient.networkInterceptors().add(new StethoInterceptor());
        return okHttpClient;
    }

    /* synthetic */ boolean lambda$provideDebugOkHttp2Client$0(String str, SSLSession sSLSession) {
        return acceptHost(str);
    }

    @Provides
    @Named("releaseOkHttp2Client")
    @ApplicationScope
    public OkHttpClient provideReleaseOkHttpClient(OkHttpConfig okHttpConfig) {
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(okHttpConfig.getConnectTimeout(), TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(okHttpConfig.getReadTimeout(), TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(okHttpConfig.getWriteTimeout(), TimeUnit.SECONDS);
        return okHttpClient;
    }

    @Provides
    @ApplicationScope
    public OkHttpClient provideOkHttpClient(@Named("releaseOkHttp2Client") Provider<OkHttpClient> provider, @Named("debugOkHttp2Client") Provider<OkHttpClient> provider2) {
        return (OkHttpClient) provider.get();
    }

    @Provides
    @ApplicationScope
    public Client provideOkClient(OkHttpClient okHttpClient) {
        return new OkClient(okHttpClient);
    }

    @Provides
    @Named("httpBodyLogging")
    @ApplicationScope
    public HttpLoggingInterceptor provideHttpBodyLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(Level.BODY);
        return httpLoggingInterceptor;
    }

    @Provides
    @Named("debugOkHttp3Client")
    @ApplicationScope
    public okhttp3.OkHttpClient provideDebugOkHttp3Client(OkHttpConfig okHttpConfig, @Named("httpBodyLogging") HttpLoggingInterceptor httpLoggingInterceptor, SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
        return new Builder().connectTimeout(okHttpConfig.getConnectTimeout(), TimeUnit.SECONDS).readTimeout(okHttpConfig.getReadTimeout(), TimeUnit.SECONDS).writeTimeout(okHttpConfig.getWriteTimeout(), TimeUnit.SECONDS).sslSocketFactory(sSLSocketFactory, x509TrustManager).hostnameVerifier(OkHttpModule$$Lambda$2.lambdaFactory$(this)).addNetworkInterceptor(new com.facebook.stetho.okhttp3.StethoInterceptor()).addInterceptor(httpLoggingInterceptor).build();
    }

    /* synthetic */ boolean lambda$provideDebugOkHttp3Client$1(String str, SSLSession sSLSession) {
        return acceptHost(str);
    }

    @Provides
    @Named("releaseOkHttp3Client")
    @ApplicationScope
    public okhttp3.OkHttpClient provideSafeOkHttp3Client(OkHttpConfig okHttpConfig) {
        return new Builder().connectTimeout(okHttpConfig.getConnectTimeout(), TimeUnit.SECONDS).readTimeout(okHttpConfig.getReadTimeout(), TimeUnit.SECONDS).writeTimeout(okHttpConfig.getWriteTimeout(), TimeUnit.SECONDS).build();
    }

    @Provides
    @ApplicationScope
    public okhttp3.OkHttpClient provideOkHttp3Client(@Named("releaseOkHttp3Client") Provider<okhttp3.OkHttpClient> provider, @Named("debugOkHttp3Client") Provider<okhttp3.OkHttpClient> provider2) {
        return (okhttp3.OkHttpClient) provider.get();
    }
}
