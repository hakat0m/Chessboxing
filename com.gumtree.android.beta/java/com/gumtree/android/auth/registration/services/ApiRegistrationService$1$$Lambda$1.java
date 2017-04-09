package com.gumtree.android.auth.registration.services;

import com.gumtree.android.auth.registration.services.ApiRegistrationService.1;
import com.gumtree.android.auth.trust.TrustHandler.Callback;
import java.lang.invoke.LambdaForm.Hidden;
import rx.Subscriber;

final /* synthetic */ class ApiRegistrationService$1$$Lambda$1 implements Callback {
    private final 1 arg$1;
    private final Subscriber arg$2;

    private ApiRegistrationService$1$$Lambda$1(1 1, Subscriber subscriber) {
        this.arg$1 = 1;
        this.arg$2 = subscriber;
    }

    public static Callback lambdaFactory$(1 1, Subscriber subscriber) {
        return new ApiRegistrationService$1$$Lambda$1(1, subscriber);
    }

    @Hidden
    public void onSessionId(String str) {
        this.arg$1.lambda$call$0(this.arg$2, str);
    }
}
