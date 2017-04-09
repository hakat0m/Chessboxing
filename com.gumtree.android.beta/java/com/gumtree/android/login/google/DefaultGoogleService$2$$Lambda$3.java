package com.gumtree.android.login.google;

import android.app.Activity;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.gumtree.android.auth.model.AuthResult;
import com.gumtree.android.login.google.DefaultGoogleService.2;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class DefaultGoogleService$2$$Lambda$3 implements ResultCallback {
    private final 2 arg$1;
    private final Activity arg$2;
    private final Subscriber arg$3;
    private final AuthResult arg$4;

    private DefaultGoogleService$2$$Lambda$3(2 2, Activity activity, Subscriber subscriber, AuthResult authResult) {
        this.arg$1 = 2;
        this.arg$2 = activity;
        this.arg$3 = subscriber;
        this.arg$4 = authResult;
    }

    public static ResultCallback lambdaFactory$(2 2, Activity activity, Subscriber subscriber, AuthResult authResult) {
        return new DefaultGoogleService$2$$Lambda$3(2, activity, subscriber, authResult);
    }

    @Hidden
    public void onResult(Result result) {
        this.arg$1.lambda$null$0(this.arg$2, this.arg$3, this.arg$4, (Status) result);
    }
}
