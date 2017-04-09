package com.gumtree.android.auth.registration.services;

import com.ebay.classifieds.capi.users.RegisterUser;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class ApiRegistrationService$$Lambda$1 implements Func1 {
    private final ApiRegistrationService arg$1;
    private final RegisterUser arg$2;

    private ApiRegistrationService$$Lambda$1(ApiRegistrationService apiRegistrationService, RegisterUser registerUser) {
        this.arg$1 = apiRegistrationService;
        this.arg$2 = registerUser;
    }

    public static Func1 lambdaFactory$(ApiRegistrationService apiRegistrationService, RegisterUser registerUser) {
        return new ApiRegistrationService$$Lambda$1(apiRegistrationService, registerUser);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$register$1(this.arg$2, (String) obj);
    }
}
