package com.gumtree.android.auth.google;

import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedGoogleLoginView$$Lambda$7 implements Action {
    private final View arg$1;

    private GatedGoogleLoginView$$Lambda$7(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedGoogleLoginView$$Lambda$7(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showProgress();
    }
}
