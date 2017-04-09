package com.gumtree.android.deeplinking.search;

import com.gumtree.android.deeplinking.search.SearchDeepLinkingPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedSearchDeepLinkingView$$Lambda$1 implements Action1 {
    private final GatedSearchDeepLinkingView arg$1;

    private GatedSearchDeepLinkingView$$Lambda$1(GatedSearchDeepLinkingView gatedSearchDeepLinkingView) {
        this.arg$1 = gatedSearchDeepLinkingView;
    }

    public static Action1 lambdaFactory$(GatedSearchDeepLinkingView gatedSearchDeepLinkingView) {
        return new GatedSearchDeepLinkingView$$Lambda$1(gatedSearchDeepLinkingView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
