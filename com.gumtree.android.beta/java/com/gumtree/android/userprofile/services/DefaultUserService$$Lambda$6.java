package com.gumtree.android.userprofile.services;

import com.ebay.classifieds.capi.users.profile.UserProfile;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultUserService$$Lambda$6 implements Action1 {
    private final DefaultUserService arg$1;

    private DefaultUserService$$Lambda$6(DefaultUserService defaultUserService) {
        this.arg$1 = defaultUserService;
    }

    public static Action1 lambdaFactory$(DefaultUserService defaultUserService) {
        return new DefaultUserService$$Lambda$6(defaultUserService);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$update$3((UserProfile) obj);
    }
}
