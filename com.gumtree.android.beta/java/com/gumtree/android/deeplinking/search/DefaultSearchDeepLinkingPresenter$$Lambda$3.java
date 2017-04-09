package com.gumtree.android.deeplinking.search;

import com.gumtree.android.services.NetworkState;
import java.lang.invoke.LambdaForm.Hidden;
import java.net.URI;
import rx.functions.Func2;

final /* synthetic */ class DefaultSearchDeepLinkingPresenter$$Lambda$3 implements Func2 {
    private final DefaultSearchDeepLinkingPresenter arg$1;

    private DefaultSearchDeepLinkingPresenter$$Lambda$3(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter) {
        this.arg$1 = defaultSearchDeepLinkingPresenter;
    }

    public static Func2 lambdaFactory$(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter) {
        return new DefaultSearchDeepLinkingPresenter$$Lambda$3(defaultSearchDeepLinkingPresenter);
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return this.arg$1.lambda$attachView$2((NetworkState) obj, (URI) obj2);
    }
}
