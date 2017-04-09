package com.gumtree.android.auth.login.services;

import com.ebay.classifieds.capi.users.login.UserLogins;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLoginService$$Lambda$1 implements Func1 {
    private static final ApiLoginService$$Lambda$1 instance = new ApiLoginService$$Lambda$1();

    private ApiLoginService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiLoginService.lambda$login$0((UserLogins) obj);
    }
}
