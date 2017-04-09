package com.gumtree.android.login.google;

import com.gumtree.android.login.google.DefaultGoogleService.1;
import com.gumtree.android.login.google.DefaultGoogleService.OnFailure;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class DefaultGoogleService$1$$Lambda$2 implements OnFailure {
    private final Subscriber arg$1;

    private DefaultGoogleService$1$$Lambda$2(Subscriber subscriber) {
        this.arg$1 = subscriber;
    }

    public static OnFailure lambdaFactory$(Subscriber subscriber) {
        return new DefaultGoogleService$1$$Lambda$2(subscriber);
    }

    @Hidden
    public void failed(Throwable th) {
        1.lambda$call$2(this.arg$1, th);
    }
}
