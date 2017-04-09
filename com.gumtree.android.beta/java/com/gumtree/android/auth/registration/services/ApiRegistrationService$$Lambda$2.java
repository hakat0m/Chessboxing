package com.gumtree.android.auth.registration.services;

import com.ebay.classifieds.capi.users.RegisterUser;
import java.lang.invoke.LambdaForm.Hidden;
import retrofit.client.Response;
import rx.functions.Func1;

final /* synthetic */ class ApiRegistrationService$$Lambda$2 implements Func1 {
    private final RegisterUser arg$1;

    private ApiRegistrationService$$Lambda$2(RegisterUser registerUser) {
        this.arg$1 = registerUser;
    }

    public static Func1 lambdaFactory$(RegisterUser registerUser) {
        return new ApiRegistrationService$$Lambda$2(registerUser);
    }

    @Hidden
    public Object call(Object obj) {
        return ApiRegistrationService.lambda$null$0(this.arg$1, (Response) obj);
    }
}
