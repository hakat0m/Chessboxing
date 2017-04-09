package com.gumtree.android.deeplinking.activate;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import java.net.URI;
import rx.functions.Func2;

final /* synthetic */ class DefaultActivateDeepLinkingPresenter$$Lambda$1 implements Func2 {
    private final DefaultActivateDeepLinkingPresenter arg$1;

    private DefaultActivateDeepLinkingPresenter$$Lambda$1(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        this.arg$1 = defaultActivateDeepLinkingPresenter;
    }

    public static Func2 lambdaFactory$(DefaultActivateDeepLinkingPresenter defaultActivateDeepLinkingPresenter) {
        return new DefaultActivateDeepLinkingPresenter$$Lambda$1(defaultActivateDeepLinkingPresenter);
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return this.arg$1.lambda$attachView$0((NetworkState) obj, (URI) obj2);
    }
}
