package com.gumtree.android.auth.facebook;

import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedFacebookLoginView$$Lambda$6 implements Action {
    private final View arg$1;

    private GatedFacebookLoginView$$Lambda$6(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedFacebookLoginView$$Lambda$6(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showNoNetworkError();
    }
}
