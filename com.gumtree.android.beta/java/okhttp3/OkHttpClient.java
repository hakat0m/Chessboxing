package okhttp3;

import java.net.Proxy;
import java.net.ProxySelector;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.net.SocketFactory;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import okhttp3.internal.Internal;
import okhttp3.internal.Util;
import okhttp3.internal.cache.InternalCache;
import okhttp3.internal.platform.Platform;
import okhttp3.internal.tls.CertificateChainCleaner;
import okhttp3.internal.tls.OkHostnameVerifier;

public class OkHttpClient implements Cloneable, Call$Factory {
    private static final List<ConnectionSpec> DEFAULT_CONNECTION_SPECS = Util.immutableList(new ConnectionSpec[]{ConnectionSpec.MODERN_TLS, ConnectionSpec.COMPATIBLE_TLS, ConnectionSpec.CLEARTEXT});
    private static final List<Protocol> DEFAULT_PROTOCOLS = Util.immutableList(new Protocol[]{Protocol.HTTP_2, Protocol.SPDY_3, Protocol.HTTP_1_1});
    final Authenticator authenticator;
    final Cache cache;
    final CertificateChainCleaner certificateChainCleaner;
    final CertificatePinner certificatePinner;
    final int connectTimeout;
    final ConnectionPool connectionPool;
    final List<ConnectionSpec> connectionSpecs;
    final CookieJar cookieJar;
    final Dispatcher dispatcher;
    final Dns dns;
    final boolean followRedirects;
    final boolean followSslRedirects;
    final HostnameVerifier hostnameVerifier;
    final List<Interceptor> interceptors;
    final InternalCache internalCache;
    final List<Interceptor> networkInterceptors;
    final List<Protocol> protocols;
    final Proxy proxy;
    final Authenticator proxyAuthenticator;
    final ProxySelector proxySelector;
    final int readTimeout;
    final boolean retryOnConnectionFailure;
    final SocketFactory socketFactory;
    final SSLSocketFactory sslSocketFactory;
    final int writeTimeout;

    public final class Builder {
        Authenticator authenticator;
        Cache cache;
        CertificateChainCleaner certificateChainCleaner;
        CertificatePinner certificatePinner;
        int connectTimeout;
        ConnectionPool connectionPool;
        List<ConnectionSpec> connectionSpecs;
        CookieJar cookieJar;
        Dispatcher dispatcher;
        Dns dns;
        boolean followRedirects;
        boolean followSslRedirects;
        HostnameVerifier hostnameVerifier;
        final List<Interceptor> interceptors;
        InternalCache internalCache;
        final List<Interceptor> networkInterceptors;
        List<Protocol> protocols;
        Proxy proxy;
        Authenticator proxyAuthenticator;
        ProxySelector proxySelector;
        int readTimeout;
        boolean retryOnConnectionFailure;
        SocketFactory socketFactory;
        SSLSocketFactory sslSocketFactory;
        int writeTimeout;

        public Builder() {
            this.interceptors = new ArrayList();
            this.networkInterceptors = new ArrayList();
            this.dispatcher = new Dispatcher();
            this.protocols = OkHttpClient.DEFAULT_PROTOCOLS;
            this.connectionSpecs = OkHttpClient.DEFAULT_CONNECTION_SPECS;
            this.proxySelector = ProxySelector.getDefault();
            this.cookieJar = CookieJar.NO_COOKIES;
            this.socketFactory = SocketFactory.getDefault();
            this.hostnameVerifier = OkHostnameVerifier.INSTANCE;
            this.certificatePinner = CertificatePinner.DEFAULT;
            this.proxyAuthenticator = Authenticator.NONE;
            this.authenticator = Authenticator.NONE;
            this.connectionPool = new ConnectionPool();
            this.dns = Dns.SYSTEM;
            this.followSslRedirects = true;
            this.followRedirects = true;
            this.retryOnConnectionFailure = true;
            this.connectTimeout = 10000;
            this.readTimeout = 10000;
            this.writeTimeout = 10000;
        }

        Builder(OkHttpClient okHttpClient) {
            this.interceptors = new ArrayList();
            this.networkInterceptors = new ArrayList();
            this.dispatcher = okHttpClient.dispatcher;
            this.proxy = okHttpClient.proxy;
            this.protocols = okHttpClient.protocols;
            this.connectionSpecs = okHttpClient.connectionSpecs;
            this.interceptors.addAll(okHttpClient.interceptors);
            this.networkInterceptors.addAll(okHttpClient.networkInterceptors);
            this.proxySelector = okHttpClient.proxySelector;
            this.cookieJar = okHttpClient.cookieJar;
            this.internalCache = okHttpClient.internalCache;
            this.cache = okHttpClient.cache;
            this.socketFactory = okHttpClient.socketFactory;
            this.sslSocketFactory = okHttpClient.sslSocketFactory;
            this.certificateChainCleaner = okHttpClient.certificateChainCleaner;
            this.hostnameVerifier = okHttpClient.hostnameVerifier;
            this.certificatePinner = okHttpClient.certificatePinner;
            this.proxyAuthenticator = okHttpClient.proxyAuthenticator;
            this.authenticator = okHttpClient.authenticator;
            this.connectionPool = okHttpClient.connectionPool;
            this.dns = okHttpClient.dns;
            this.followSslRedirects = okHttpClient.followSslRedirects;
            this.followRedirects = okHttpClient.followRedirects;
            this.retryOnConnectionFailure = okHttpClient.retryOnConnectionFailure;
            this.connectTimeout = okHttpClient.connectTimeout;
            this.readTimeout = okHttpClient.readTimeout;
            this.writeTimeout = okHttpClient.writeTimeout;
        }

        public Builder connectTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.connectTimeout = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }

        public Builder readTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.readTimeout = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }

        public Builder writeTimeout(long j, TimeUnit timeUnit) {
            if (j < 0) {
                throw new IllegalArgumentException("timeout < 0");
            } else if (timeUnit == null) {
                throw new NullPointerException("unit == null");
            } else {
                long toMillis = timeUnit.toMillis(j);
                if (toMillis > 2147483647L) {
                    throw new IllegalArgumentException("Timeout too large.");
                } else if (toMillis != 0 || j <= 0) {
                    this.writeTimeout = (int) toMillis;
                    return this;
                } else {
                    throw new IllegalArgumentException("Timeout too small.");
                }
            }
        }

        public Builder proxy(Proxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder proxySelector(ProxySelector proxySelector) {
            this.proxySelector = proxySelector;
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            if (cookieJar == null) {
                throw new NullPointerException("cookieJar == null");
            }
            this.cookieJar = cookieJar;
            return this;
        }

        void setInternalCache(InternalCache internalCache) {
            this.internalCache = internalCache;
            this.cache = null;
        }

        public Builder cache(Cache cache) {
            this.cache = cache;
            this.internalCache = null;
            return this;
        }

        public Builder dns(Dns dns) {
            if (dns == null) {
                throw new NullPointerException("dns == null");
            }
            this.dns = dns;
            return this;
        }

        public Builder socketFactory(SocketFactory socketFactory) {
            if (socketFactory == null) {
                throw new NullPointerException("socketFactory == null");
            }
            this.socketFactory = socketFactory;
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            }
            X509TrustManager trustManager = Platform.get().trustManager(sSLSocketFactory);
            if (trustManager == null) {
                throw new IllegalStateException("Unable to extract the trust manager on " + Platform.get() + ", sslSocketFactory is " + sSLSocketFactory.getClass());
            }
            this.sslSocketFactory = sSLSocketFactory;
            this.certificateChainCleaner = CertificateChainCleaner.get(trustManager);
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sSLSocketFactory, X509TrustManager x509TrustManager) {
            if (sSLSocketFactory == null) {
                throw new NullPointerException("sslSocketFactory == null");
            } else if (x509TrustManager == null) {
                throw new NullPointerException("trustManager == null");
            } else {
                this.sslSocketFactory = sSLSocketFactory;
                this.certificateChainCleaner = CertificateChainCleaner.get(x509TrustManager);
                return this;
            }
        }

        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier == null) {
                throw new NullPointerException("hostnameVerifier == null");
            }
            this.hostnameVerifier = hostnameVerifier;
            return this;
        }

        public Builder certificatePinner(CertificatePinner certificatePinner) {
            if (certificatePinner == null) {
                throw new NullPointerException("certificatePinner == null");
            }
            this.certificatePinner = certificatePinner;
            return this;
        }

        public Builder authenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("authenticator == null");
            }
            this.authenticator = authenticator;
            return this;
        }

        public Builder proxyAuthenticator(Authenticator authenticator) {
            if (authenticator == null) {
                throw new NullPointerException("proxyAuthenticator == null");
            }
            this.proxyAuthenticator = authenticator;
            return this;
        }

        public Builder connectionPool(ConnectionPool connectionPool) {
            if (connectionPool == null) {
                throw new NullPointerException("connectionPool == null");
            }
            this.connectionPool = connectionPool;
            return this;
        }

        public Builder followSslRedirects(boolean z) {
            this.followSslRedirects = z;
            return this;
        }

        public Builder followRedirects(boolean z) {
            this.followRedirects = z;
            return this;
        }

        public Builder retryOnConnectionFailure(boolean z) {
            this.retryOnConnectionFailure = z;
            return this;
        }

        public Builder dispatcher(Dispatcher dispatcher) {
            if (dispatcher == null) {
                throw new IllegalArgumentException("dispatcher == null");
            }
            this.dispatcher = dispatcher;
            return this;
        }

        public Builder protocols(List<Protocol> list) {
            List immutableList = Util.immutableList(list);
            if (!immutableList.contains(Protocol.HTTP_1_1)) {
                throw new IllegalArgumentException("protocols doesn't contain http/1.1: " + immutableList);
            } else if (immutableList.contains(Protocol.HTTP_1_0)) {
                throw new IllegalArgumentException("protocols must not contain http/1.0: " + immutableList);
            } else if (immutableList.contains(null)) {
                throw new IllegalArgumentException("protocols must not contain null");
            } else {
                this.protocols = Util.immutableList(immutableList);
                return this;
            }
        }

        public Builder connectionSpecs(List<ConnectionSpec> list) {
            this.connectionSpecs = Util.immutableList(list);
            return this;
        }

        public List<Interceptor> interceptors() {
            return this.interceptors;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            this.interceptors.add(interceptor);
            return this;
        }

        public List<Interceptor> networkInterceptors() {
            return this.networkInterceptors;
        }

        public Builder addNetworkInterceptor(Interceptor interceptor) {
            this.networkInterceptors.add(interceptor);
            return this;
        }

        public OkHttpClient build() {
            return new OkHttpClient();
        }
    }

    static {
        Internal.instance = new 1();
    }

    public OkHttpClient() {
        this(new Builder());
    }

    private OkHttpClient(Builder builder) {
        this.dispatcher = builder.dispatcher;
        this.proxy = builder.proxy;
        this.protocols = builder.protocols;
        this.connectionSpecs = builder.connectionSpecs;
        this.interceptors = Util.immutableList(builder.interceptors);
        this.networkInterceptors = Util.immutableList(builder.networkInterceptors);
        this.proxySelector = builder.proxySelector;
        this.cookieJar = builder.cookieJar;
        this.cache = builder.cache;
        this.internalCache = builder.internalCache;
        this.socketFactory = builder.socketFactory;
        Object obj = null;
        for (ConnectionSpec connectionSpec : this.connectionSpecs) {
            Object obj2;
            if (obj != null || connectionSpec.isTls()) {
                obj2 = 1;
            } else {
                obj2 = null;
            }
            obj = obj2;
        }
        if (builder.sslSocketFactory != null || obj == null) {
            this.sslSocketFactory = builder.sslSocketFactory;
            this.certificateChainCleaner = builder.certificateChainCleaner;
        } else {
            X509TrustManager systemDefaultTrustManager = systemDefaultTrustManager();
            this.sslSocketFactory = systemDefaultSslSocketFactory(systemDefaultTrustManager);
            this.certificateChainCleaner = CertificateChainCleaner.get(systemDefaultTrustManager);
        }
        this.hostnameVerifier = builder.hostnameVerifier;
        this.certificatePinner = builder.certificatePinner.withCertificateChainCleaner(this.certificateChainCleaner);
        this.proxyAuthenticator = builder.proxyAuthenticator;
        this.authenticator = builder.authenticator;
        this.connectionPool = builder.connectionPool;
        this.dns = builder.dns;
        this.followSslRedirects = builder.followSslRedirects;
        this.followRedirects = builder.followRedirects;
        this.retryOnConnectionFailure = builder.retryOnConnectionFailure;
        this.connectTimeout = builder.connectTimeout;
        this.readTimeout = builder.readTimeout;
        this.writeTimeout = builder.writeTimeout;
    }

    private X509TrustManager systemDefaultTrustManager() {
        try {
            TrustManagerFactory instance = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            instance.init((KeyStore) null);
            TrustManager[] trustManagers = instance.getTrustManagers();
            if (trustManagers.length == 1 && (trustManagers[0] instanceof X509TrustManager)) {
                return (X509TrustManager) trustManagers[0];
            }
            throw new IllegalStateException("Unexpected default trust managers:" + Arrays.toString(trustManagers));
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }

    private SSLSocketFactory systemDefaultSslSocketFactory(X509TrustManager x509TrustManager) {
        try {
            SSLContext instance = SSLContext.getInstance("TLS");
            instance.init(null, new TrustManager[]{x509TrustManager}, null);
            return instance.getSocketFactory();
        } catch (GeneralSecurityException e) {
            throw new AssertionError();
        }
    }

    public int connectTimeoutMillis() {
        return this.connectTimeout;
    }

    public int readTimeoutMillis() {
        return this.readTimeout;
    }

    public int writeTimeoutMillis() {
        return this.writeTimeout;
    }

    public Proxy proxy() {
        return this.proxy;
    }

    public ProxySelector proxySelector() {
        return this.proxySelector;
    }

    public CookieJar cookieJar() {
        return this.cookieJar;
    }

    public Cache cache() {
        return this.cache;
    }

    InternalCache internalCache() {
        return this.cache != null ? this.cache.internalCache : this.internalCache;
    }

    public Dns dns() {
        return this.dns;
    }

    public SocketFactory socketFactory() {
        return this.socketFactory;
    }

    public SSLSocketFactory sslSocketFactory() {
        return this.sslSocketFactory;
    }

    public HostnameVerifier hostnameVerifier() {
        return this.hostnameVerifier;
    }

    public CertificatePinner certificatePinner() {
        return this.certificatePinner;
    }

    public Authenticator authenticator() {
        return this.authenticator;
    }

    public Authenticator proxyAuthenticator() {
        return this.proxyAuthenticator;
    }

    public ConnectionPool connectionPool() {
        return this.connectionPool;
    }

    public boolean followSslRedirects() {
        return this.followSslRedirects;
    }

    public boolean followRedirects() {
        return this.followRedirects;
    }

    public boolean retryOnConnectionFailure() {
        return this.retryOnConnectionFailure;
    }

    public Dispatcher dispatcher() {
        return this.dispatcher;
    }

    public List<Protocol> protocols() {
        return this.protocols;
    }

    public List<ConnectionSpec> connectionSpecs() {
        return this.connectionSpecs;
    }

    public List<Interceptor> interceptors() {
        return this.interceptors;
    }

    public List<Interceptor> networkInterceptors() {
        return this.networkInterceptors;
    }

    public Call newCall(Request request) {
        return new RealCall(this, request);
    }

    public Builder newBuilder() {
        return new Builder(this);
    }
}
