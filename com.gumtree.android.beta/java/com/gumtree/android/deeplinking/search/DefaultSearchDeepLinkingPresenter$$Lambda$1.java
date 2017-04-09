package com.gumtree.android.deeplinking.search;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;
import rx.functions.Action1;

final /* synthetic */ class DefaultSearchDeepLinkingPresenter$$Lambda$1 implements Action1 {
    private final DefaultSearchDeepLinkingPresenter arg$1;
    private final String arg$2;

    private DefaultSearchDeepLinkingPresenter$$Lambda$1(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter, String str) {
        this.arg$1 = defaultSearchDeepLinkingPresenter;
        this.arg$2 = str;
    }

    public static Action1 lambdaFactory$(DefaultSearchDeepLinkingPresenter defaultSearchDeepLinkingPresenter, String str) {
        return new DefaultSearchDeepLinkingPresenter$$Lambda$1(defaultSearchDeepLinkingPresenter, str);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$doAnalyse$0(this.arg$2, (Pair) obj);
    }
}
