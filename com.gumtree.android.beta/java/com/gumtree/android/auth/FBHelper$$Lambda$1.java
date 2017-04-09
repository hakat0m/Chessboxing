package com.gumtree.android.auth;

import com.facebook.Request.Callback;
import com.facebook.Response;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class FBHelper$$Lambda$1 implements Callback {
    private static final FBHelper$$Lambda$1 instance = new FBHelper$$Lambda$1();

    private FBHelper$$Lambda$1() {
    }

    public static Callback lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onCompleted(Response response) {
        FBHelper.clearTokenInformation();
    }
}
