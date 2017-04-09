package com.gumtree.android.auth.login.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiLoginService$$Lambda$6 implements Func1 {
    private final AuthResult arg$1;

    private ApiLoginService$$Lambda$6(AuthResult authResult) {
        this.arg$1 = authResult;
    }

    public static Func1 lambdaFactory$(AuthResult authResult) {
        return new ApiLoginService$$Lambda$6(authResult);
    }

    @Hidden
    public Object call(Object obj) {
        return ApiLoginService.lambda$null$3(this.arg$1, (UserProfile) obj);
    }
}
