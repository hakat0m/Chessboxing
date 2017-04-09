package com.gumtree.android.auth.google;

import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedGoogleLoginView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedGoogleLoginView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedGoogleLoginView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showNoNetwork();
    }
}
