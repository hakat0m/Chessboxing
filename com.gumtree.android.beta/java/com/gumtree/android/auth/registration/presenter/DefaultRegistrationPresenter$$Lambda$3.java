package com.gumtree.android.auth.registration.presenter;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultRegistrationPresenter$$Lambda$3 implements Action1 {
    private final DefaultRegistrationPresenter arg$1;

    private DefaultRegistrationPresenter$$Lambda$3(DefaultRegistrationPresenter defaultRegistrationPresenter) {
        this.arg$1 = defaultRegistrationPresenter;
    }

    public static Action1 lambdaFactory$(DefaultRegistrationPresenter defaultRegistrationPresenter) {
        return new DefaultRegistrationPresenter$$Lambda$3(defaultRegistrationPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$2((NetworkState) obj);
    }
}
