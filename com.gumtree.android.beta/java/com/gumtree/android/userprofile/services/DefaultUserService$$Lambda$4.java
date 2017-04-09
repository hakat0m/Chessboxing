package com.gumtree.android.userprofile.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultUserService$$Lambda$4 implements Action0 {
    private final DefaultUserService arg$1;

    private DefaultUserService$$Lambda$4(DefaultUserService defaultUserService) {
        this.arg$1 = defaultUserService;
    }

    public static Action0 lambdaFactory$(DefaultUserService defaultUserService) {
        return new DefaultUserService$$Lambda$4(defaultUserService);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$update$1();
    }
}
