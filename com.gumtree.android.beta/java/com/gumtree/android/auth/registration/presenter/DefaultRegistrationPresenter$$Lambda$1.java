package com.gumtree.android.auth.registration.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultRegistrationPresenter$$Lambda$1 implements Action1 {
    private final DefaultRegistrationPresenter arg$1;
    private final String arg$2;

    private DefaultRegistrationPresenter$$Lambda$1(DefaultRegistrationPresenter defaultRegistrationPresenter, String str) {
        this.arg$1 = defaultRegistrationPresenter;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(DefaultRegistrationPresenter defaultRegistrationPresenter, String str) {
        return new DefaultRegistrationPresenter$$Lambda$1(defaultRegistrationPresenter, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$register$0(this.arg$2, (String) obj);
    }
}
