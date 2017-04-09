package com.gumtree.android.auth.google;

import com.gumtree.android.auth.google.presenter.GoogleLoginPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedGoogleLoginView$$Lambda$1 implements Action1 {
    private final GatedGoogleLoginView arg$1;

    private GatedGoogleLoginView$$Lambda$1(GatedGoogleLoginView gatedGoogleLoginView) {
        this.arg$1 = gatedGoogleLoginView;
    }

    public static Action1 lambdaFactory$(GatedGoogleLoginView gatedGoogleLoginView) {
        return new GatedGoogleLoginView$$Lambda$1(gatedGoogleLoginView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
