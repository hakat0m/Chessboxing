package com.gumtree.android.auth.registration.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultRegistrationPresenter$$Lambda$2 implements Action1 {
    private final DefaultRegistrationPresenter arg$1;

    private DefaultRegistrationPresenter$$Lambda$2(DefaultRegistrationPresenter defaultRegistrationPresenter) {
        this.arg$1 = defaultRegistrationPresenter;
    }

    public static Action1 lambdaFactory$(DefaultRegistrationPresenter defaultRegistrationPresenter) {
        return new DefaultRegistrationPresenter$$Lambda$2(defaultRegistrationPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$register$1((Throwable) obj);
    }
}
