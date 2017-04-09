package com.gumtree.android.dagger.modules;

import java.lang.invoke.LambdaForm.Hidden;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

final /* synthetic */ class OkHttpModule$$Lambda$1 implements HostnameVerifier {
    private final OkHttpModule arg$1;

    private OkHttpModule$$Lambda$1(OkHttpModule okHttpModule) {
        this.arg$1 = okHttpModule;
    }

    public static HostnameVerifier lambdaFactory$(OkHttpModule okHttpModule) {
        return new OkHttpModule$$Lambda$1(okHttpModule);
    }

    @Hidden
    public boolean verify(String str, SSLSession sSLSession) {
        return this.arg$1.lambda$provideDebugOkHttp2Client$0(str, sSLSession);
    }
}
