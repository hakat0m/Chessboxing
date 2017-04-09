package com.gumtree.android.auth.forgotpassword;

import com.gumtree.android.auth.forgotpassword.presenter.ForgotPasswordPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedForgotPasswordView$$Lambda$1 implements Action1 {
    private final GatedForgotPasswordView arg$1;

    private GatedForgotPasswordView$$Lambda$1(GatedForgotPasswordView gatedForgotPasswordView) {
        this.arg$1 = gatedForgotPasswordView;
    }

    public static Action1 lambdaFactory$(GatedForgotPasswordView gatedForgotPasswordView) {
        return new GatedForgotPasswordView$$Lambda$1(gatedForgotPasswordView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
