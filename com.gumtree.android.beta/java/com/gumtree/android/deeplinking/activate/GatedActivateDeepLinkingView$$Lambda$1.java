package com.gumtree.android.deeplinking.activate;

import com.gumtree.android.deeplinking.activate.ActivateDeepLinkingPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedActivateDeepLinkingView$$Lambda$1 implements Action1 {
    private final GatedActivateDeepLinkingView arg$1;

    private GatedActivateDeepLinkingView$$Lambda$1(GatedActivateDeepLinkingView gatedActivateDeepLinkingView) {
        this.arg$1 = gatedActivateDeepLinkingView;
    }

    public static Action1 lambdaFactory$(GatedActivateDeepLinkingView gatedActivateDeepLinkingView) {
        return new GatedActivateDeepLinkingView$$Lambda$1(gatedActivateDeepLinkingView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
