package com.gumtree.android.auth.login.services;

import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLoginService$$Lambda$4 implements Func1 {
    private final ApiLoginService arg$1;

    private ApiLoginService$$Lambda$4(ApiLoginService apiLoginService) {
        this.arg$1 = apiLoginService;
    }

    public static Func1 lambdaFactory$(ApiLoginService apiLoginService) {
        return new ApiLoginService$$Lambda$4(apiLoginService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$login$4((AuthResult) obj);
    }
}
