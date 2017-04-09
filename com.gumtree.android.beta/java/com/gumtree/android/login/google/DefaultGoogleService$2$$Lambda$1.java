package com.gumtree.android.login.google;

import android.app.Activity;
import com.google.android.gms.auth.api.credentials.Credential;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.login.google.DefaultGoogleService.2;
import com.gumtree.android.login.google.DefaultGoogleService.OnConnected;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class DefaultGoogleService$2$$Lambda$1 implements OnConnected {
    private final 2 arg$1;
    private final Credential arg$2;
    private final Activity arg$3;
    private final Subscriber arg$4;
    private final AuthResult arg$5;

    private DefaultGoogleService$2$$Lambda$1(2 2, Credential credential, Activity activity, Subscriber subscriber, AuthResult authResult) {
        this.arg$1 = 2;
        this.arg$2 = credential;
        this.arg$3 = activity;
        this.arg$4 = subscriber;
        this.arg$5 = authResult;
    }

    public static OnConnected lambdaFactory$(2 2, Credential credential, Activity activity, Subscriber subscriber, AuthResult authResult) {
        return new DefaultGoogleService$2$$Lambda$1(2, credential, activity, subscriber, authResult);
    }

    @Hidden
    public void connected() {
        this.arg$1.lambda$saveSmartLock$1(this.arg$2, this.arg$3, this.arg$4, this.arg$5);
    }
}
