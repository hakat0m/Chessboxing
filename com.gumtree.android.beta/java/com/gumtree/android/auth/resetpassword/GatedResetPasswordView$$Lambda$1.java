package com.gumtree.android.auth.resetpassword;

import com.gumtree.android.auth.resetpassword.presenter.ResetPasswordPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedResetPasswordView$$Lambda$1 implements Action1 {
    private final GatedResetPasswordView arg$1;

    private GatedResetPasswordView$$Lambda$1(GatedResetPasswordView gatedResetPasswordView) {
        this.arg$1 = gatedResetPasswordView;
    }

    public static Action1 lambdaFactory$(GatedResetPasswordView gatedResetPasswordView) {
        return new GatedResetPasswordView$$Lambda$1(gatedResetPasswordView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
