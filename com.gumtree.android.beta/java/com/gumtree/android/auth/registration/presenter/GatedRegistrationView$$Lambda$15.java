package com.gumtree.android.auth.registration.presenter;

import com.gumtree.android.auth.registration.presenter.RegistrationPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedRegistrationView$$Lambda$15 implements Action {
    private final View arg$1;

    private GatedRegistrationView$$Lambda$15(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedRegistrationView$$Lambda$15(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showLastNameErrorMessage();
    }
}
