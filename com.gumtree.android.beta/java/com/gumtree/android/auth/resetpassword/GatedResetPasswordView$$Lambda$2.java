package com.gumtree.android.auth.resetpassword;

import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedResetPasswordView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedResetPasswordView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedResetPasswordView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showLoginFragment();
    }
}
