package com.gumtree.android.deeplinking.activate;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultActivateDeepLinkingPresenter$$Lambda$3 implements Action1 {
    private final DefaultActivateDeepLinkingPresenter arg$1;

    private DefaultActivateDeepLinkingPresenter$$Lambda$3(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        this.arg$1 = defaultActivateDeepLinkingPresenter;
    }

    public static Action1 lambdaFactory$(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        return new DefaultActivateDeepLinkingPresenter$$Lambda$3(defaultActivateDeepLinkingPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$doAnalyse$2((Void) obj);
    }
}
