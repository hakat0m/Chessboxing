package com.gumtree.android.deeplinking.activate;

import java.lang.invoke.LambdaForm.Hidden;
import java.net.URI;
import rx.functions.Action1;

final /* synthetic */ class DefaultActivateDeepLinkingPresenter$$Lambda$2 implements Action1 {
    private final DefaultActivateDeepLinkingPresenter arg$1;

    private DefaultActivateDeepLinkingPresenter$$Lambda$2(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        this.arg$1 = defaultActivateDeepLinkingPresenter;
    }

    public static Action1 lambdaFactory$(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        return new DefaultActivateDeepLinkingPresenter$$Lambda$2(defaultActivateDeepLinkingPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$1((URI) obj);
    }
}
