package com.gumtree.android.userprofile.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultUserService$$Lambda$8 implements Func1 {
    private final DefaultUserService arg$1;

    private DefaultUserService$$Lambda$8(DefaultUserService defaultUserService) {
        this.arg$1 = defaultUserService;
    }

    public static Func1 lambdaFactory$(DefaultUserService defaultUserService) {
        return new DefaultUserService$$Lambda$8(defaultUserService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$update$5((Throwable) obj);
    }
}
