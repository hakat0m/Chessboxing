package com.gumtree.android.deeplinking.search;

import java.lang.invoke.LambdaForm.Hidden;
import java.net.URI;
import rx.functions.Action1;

final /* synthetic */ class DefaultSearchDeepLinkingPresenter$$Lambda$4 implements Action1 {
    private final DefaultSearchDeepLinkingPresenter arg$1;

    private DefaultSearchDeepLinkingPresenter$$Lambda$4(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter) {
        this.arg$1 = defaultSearchDeepLinkingPresenter;
    }

    public static Action1 lambdaFactory$(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter) {
        return new DefaultSearchDeepLinkingPresenter$$Lambda$4(defaultSearchDeepLinkingPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$attachView$3((URI) obj);
    }
}
