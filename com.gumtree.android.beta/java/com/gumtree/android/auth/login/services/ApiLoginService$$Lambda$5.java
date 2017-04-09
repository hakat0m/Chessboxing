package com.gumtree.android.auth.login.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import com.gumtree.android.auth.model.AuthResult;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class ApiLoginService$$Lambda$5 implements Action1 {
    private final ApiLoginService arg$1;
    private final AuthResult arg$2;

    private ApiLoginService$$Lambda$5(ApiLoginService apiLoginService, AuthResult authResult) {
        this.arg$1 = apiLoginService;
        this.arg$2 = authResult;
    }

    public static Action1 lambdaFactory$(ApiLoginService apiLoginService, AuthResult authResult) {
        return new ApiLoginService$$Lambda$5(apiLoginService, authResult);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$null$2(this.arg$2, (UserProfile) obj);
    }
}
