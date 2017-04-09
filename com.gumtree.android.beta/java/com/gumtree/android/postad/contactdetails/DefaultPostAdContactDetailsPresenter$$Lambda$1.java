package com.gumtree.android.postad.contactdetails;

import com.gumtree.android.userprofile.User;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdContactDetailsPresenter$$Lambda$1 implements Action1 {
    private final DefaultPostAdContactDetailsPresenter arg$1;

    private DefaultPostAdContactDetailsPresenter$$Lambda$1(DefaultPostAdContactDetailsPresenter defaultPostAdContactDetailsPresenter) {
        this.arg$1 = defaultPostAdContactDetailsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdContactDetailsPresenter defaultPostAdContactDetailsPresenter) {
        return new DefaultPostAdContactDetailsPresenter$$Lambda$1(defaultPostAdContactDetailsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$connectUserUpdateServiceSubscription$0((User) obj);
    }
}
