package com.gumtree.android.deeplinking.activate;

import java.lang.invoke.LambdaForm.Hidden;
import retrofit.client.Response;
import rx.functions.Func1;

final /* synthetic */ class ApiActivateUserService$$Lambda$1 implements Func1 {
    private static final ApiActivateUserService$$Lambda$1 instance = new ApiActivateUserService$$Lambda$1();

    private ApiActivateUserService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ApiActivateUserService.lambda$activate$0((Response) obj);
    }
}
