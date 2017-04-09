package com.gumtree.android.auth.forgotpassword;

import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedForgotPasswordView$$Lambda$8 implements Action {
    private final View arg$1;

    private GatedForgotPasswordView$$Lambda$8(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedForgotPasswordView$$Lambda$8(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showForgotPasswordPage();
    }
}
