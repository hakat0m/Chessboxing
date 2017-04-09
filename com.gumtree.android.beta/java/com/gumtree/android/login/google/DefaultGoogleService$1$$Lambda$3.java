package com.gumtree.android.login.google;

import android.app.Activity;
import com.google.android.gms.auth.api.credentials.CredentialRequestResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.gumtree.android.login.google.DefaultGoogleService.1;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class DefaultGoogleService$1$$Lambda$3 implements ResultCallback {
    private final 1 arg$1;
    private final Subscriber arg$2;
    private final Activity arg$3;

    private DefaultGoogleService$1$$Lambda$3(1 1, Subscriber subscriber, Activity activity) {
        this.arg$1 = 1;
        this.arg$2 = subscriber;
        this.arg$3 = activity;
    }

    public static ResultCallback lambdaFactory$(1 1, Subscriber subscriber, Activity activity) {
        return new DefaultGoogleService$1$$Lambda$3(1, subscriber, activity);
    }

    @Hidden
    public void onResult(Result result) {
        this.arg$1.lambda$null$0(this.arg$2, this.arg$3, (CredentialRequestResult) result);
    }
}
