package com.gumtree.android.auth.resetpassword;

import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter.View;
import com.gumtree.android.auth.services.validators.PasswordStrengthService$PasswordStrength;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedResetPasswordView$$Lambda$6 implements Action {
    private final View arg$1;

    private GatedResetPasswordView$$Lambda$6(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedResetPasswordView$$Lambda$6(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPasswordStrength((PasswordStrengthService$PasswordStrength) obj);
    }
}
