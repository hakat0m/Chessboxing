package com.gumtree.android.deeplinking.activate;

import com.gumtree.android.deeplinking.activate.ActivateDeepLinkingPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedActivateDeepLinkingView$$Lambda$6 implements Action {
    private final View arg$1;

    private GatedActivateDeepLinkingView$$Lambda$6(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedActivateDeepLinkingView$$Lambda$6(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showRegistration();
    }
}
