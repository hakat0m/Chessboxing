package com.gumtree.android.auth.login;

import com.gumtree.android.auth.login.presenter.LoginPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedLoginView$$Lambda$12 implements Action {
    private final View arg$1;

    private GatedLoginView$$Lambda$12(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedLoginView$$Lambda$12(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPreviousScreen();
    }
}
