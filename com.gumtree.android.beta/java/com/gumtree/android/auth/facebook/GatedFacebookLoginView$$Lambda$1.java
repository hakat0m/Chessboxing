package com.gumtree.android.auth.facebook;

import com.gumtree.android.auth.facebook.presenter.FacebookLoginPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedFacebookLoginView$$Lambda$1 implements Action1 {
    private final GatedFacebookLoginView arg$1;

    private GatedFacebookLoginView$$Lambda$1(GatedFacebookLoginView gatedFacebookLoginView) {
        this.arg$1 = gatedFacebookLoginView;
    }

    public static Action1 lambdaFactory$(GatedFacebookLoginView gatedFacebookLoginView) {
        return new GatedFacebookLoginView$$Lambda$1(gatedFacebookLoginView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
