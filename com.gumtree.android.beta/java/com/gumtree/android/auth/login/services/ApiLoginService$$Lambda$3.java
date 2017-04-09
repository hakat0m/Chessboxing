package com.gumtree.android.auth.login.services;

import com.ebay.classifieds.capi.users.login.UserLogin;
import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLoginService$$Lambda$3 implements Func1 {
    private static final ApiLoginService$$Lambda$3 instance = new ApiLoginService$$Lambda$3();

    private ApiLoginService$$Lambda$3() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new AuthResult((UserLogin) obj);
    }
}
