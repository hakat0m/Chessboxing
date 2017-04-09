package com.gumtree.android.auth.login.services;

import com.ebay.classifieds.capi.users.login.UserLogin;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class ApiLoginService$$Lambda$2 implements Action1 {
    private final ApiLoginService arg$1;
    private final String arg$2;

    private ApiLoginService$$Lambda$2(ApiLoginService apiLoginService, String str) {
        this.arg$1 = apiLoginService;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(ApiLoginService apiLoginService, String str) {
        return new ApiLoginService$$Lambda$2(apiLoginService, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$login$1(this.arg$2, (UserLogin) obj);
    }
}
