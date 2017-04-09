package com.gumtree.android.login.google;

import com.gumtree.android.login.google.DefaultGoogleService.OnFailure;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DefaultGoogleService$$Lambda$2 implements OnFailure {
    private static final DefaultGoogleService$$Lambda$2 instance = new DefaultGoogleService$$Lambda$2();

    private DefaultGoogleService$$Lambda$2() {
    }

    public static OnFailure lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void failed(Throwable th) {
        DefaultGoogleService.lambda$requestHint$1(th);
    }
}
