package com.gumtree.android.userprofile.services;

import com.gumtree.android.userprofile.User;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultUserService$$Lambda$1 implements Func1 {
    private static final DefaultUserService$$Lambda$1 instance = new DefaultUserService$$Lambda$1();

    private DefaultUserService$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultUserService.lambda$userUpdates$0((User) obj);
    }
}
