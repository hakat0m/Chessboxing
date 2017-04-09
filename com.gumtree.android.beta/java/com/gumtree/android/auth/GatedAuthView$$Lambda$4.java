package com.gumtree.android.auth;

import com.gumtree.android.auth.presenter.AuthPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedAuthView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedAuthView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedAuthView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showSplash(((Boolean) obj).booleanValue());
    }
}
