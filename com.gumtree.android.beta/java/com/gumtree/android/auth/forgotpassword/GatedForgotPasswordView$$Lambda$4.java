package com.gumtree.android.auth.forgotpassword;

import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedForgotPasswordView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedForgotPasswordView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedForgotPasswordView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showError((String) obj);
    }
}
