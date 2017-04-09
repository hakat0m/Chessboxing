package com.gumtree.android.login.google;

import com.gumtree.android.login.google.DefaultGoogleService.OnFailure;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class DefaultGoogleService$$Lambda$4 implements OnFailure {
    private final DefaultGoogleService arg$1;

    private DefaultGoogleService$$Lambda$4(DefaultGoogleService defaultGoogleService) {
        this.arg$1 = defaultGoogleService;
    }

    public static OnFailure lambdaFactory$(DefaultGoogleService defaultGoogleService) {
        return new DefaultGoogleService$$Lambda$4(defaultGoogleService);
    }

    @Hidden
    public void failed(Throwable th) {
        this.arg$1.lambda$startGoogleLogin$3(th);
    }
}
