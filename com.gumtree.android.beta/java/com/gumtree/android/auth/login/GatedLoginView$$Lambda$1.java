package com.gumtree.android.auth.login;

import com.gumtree.android.auth.login.presenter.LoginPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedLoginView$$Lambda$1 implements Action1 {
    private final GatedLoginView arg$1;

    private GatedLoginView$$Lambda$1(GatedLoginView gatedLoginView) {
        this.arg$1 = gatedLoginView;
    }

    public static Action1 lambdaFactory$(GatedLoginView gatedLoginView) {
        return new GatedLoginView$$Lambda$1(gatedLoginView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
