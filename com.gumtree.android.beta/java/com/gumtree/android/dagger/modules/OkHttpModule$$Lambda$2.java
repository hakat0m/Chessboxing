package com.gumtree.android.dagger.modules;

import java.lang.invoke.LambdaForm.Hidden;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

final /* synthetic */ class OkHttpModule$$Lambda$2 implements HostnameVerifier {
    private final OkHttpModule arg$1;

    private OkHttpModule$$Lambda$2(OkHttpModule okHttpModule) {
        this.arg$1 = okHttpModule;
    }

    public static HostnameVerifier lambdaFactory$(OkHttpModule okHttpModule) {
        return new OkHttpModule$$Lambda$2(okHttpModule);
    }

    @Hidden
    public boolean verify(String str, SSLSession sSLSession) {
        return this.arg$1.lambda$provideDebugOkHttp3Client$1(str, sSLSession);
    }
}
