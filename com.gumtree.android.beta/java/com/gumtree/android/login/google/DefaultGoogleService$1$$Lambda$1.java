package com.gumtree.android.login.google;

import android.app.Activity;
import com.google.android.gms.auth.api.credentials.CredentialRequest;
import com.gumtree.android.login.google.DefaultGoogleService.1;
import com.gumtree.android.login.google.DefaultGoogleService.OnConnected;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class DefaultGoogleService$1$$Lambda$1 implements OnConnected {
    private final 1 arg$1;
    private final CredentialRequest arg$2;
    private final Subscriber arg$3;
    private final Activity arg$4;
    private final int arg$5;

    private DefaultGoogleService$1$$Lambda$1(1 1, CredentialRequest credentialRequest, Subscriber subscriber, Activity activity, int i) {
        this.arg$1 = 1;
        this.arg$2 = credentialRequest;
        this.arg$3 = subscriber;
        this.arg$4 = activity;
        this.arg$5 = i;
    }

    public static OnConnected lambdaFactory$(1 1, CredentialRequest credentialRequest, Subscriber subscriber, Activity activity, int i) {
        return new DefaultGoogleService$1$$Lambda$1(1, credentialRequest, subscriber, activity, i);
    }

    @Hidden
    public void connected() {
        this.arg$1.lambda$call$1(this.arg$2, this.arg$3, this.arg$4, this.arg$5);
    }
}
