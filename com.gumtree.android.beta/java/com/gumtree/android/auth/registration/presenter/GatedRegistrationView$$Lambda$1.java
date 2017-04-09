package com.gumtree.android.auth.registration.presenter;

import com.gumtree.android.auth.registration.presenter.RegistrationPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedRegistrationView$$Lambda$1 implements Action1 {
    private final GatedRegistrationView arg$1;

    private GatedRegistrationView$$Lambda$1(GatedRegistrationView gatedRegistrationView) {
        this.arg$1 = gatedRegistrationView;
    }

    public static Action1 lambdaFactory$(GatedRegistrationView gatedRegistrationView) {
        return new GatedRegistrationView$$Lambda$1(gatedRegistrationView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
