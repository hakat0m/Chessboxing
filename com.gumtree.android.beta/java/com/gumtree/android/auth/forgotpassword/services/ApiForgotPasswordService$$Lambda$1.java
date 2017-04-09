package com.gumtree.android.auth.forgotpassword.services;

import java.lang.invoke.LambdaForm.Hidden;
import retrofit.client.Response;
import rx.functions.Func1;

final /* synthetic */ class ApiForgotPasswordService$$Lambda$1 implements Func1 {
    private final String arg$1;

    private ApiForgotPasswordService$$Lambda$1(String str) {
        this.arg$1 = str;
    }

    public static Func1 lambdaFactory$(String str) {
        return new ApiForgotPasswordService$$Lambda$1(str);
    }

    @Hidden
    public Object call(Object obj) {
        return ApiForgotPasswordService.lambda$forgotPassword$0(this.arg$1, (Response) obj);
    }
}
