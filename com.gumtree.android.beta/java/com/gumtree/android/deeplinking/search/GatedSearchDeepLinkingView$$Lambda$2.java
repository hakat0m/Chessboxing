package com.gumtree.android.deeplinking.search;

import com.gumtree.android.deeplinking.SearchParameters;
import com.gumtree.android.deeplinking.search.SearchDeepLinkingPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedSearchDeepLinkingView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedSearchDeepLinkingView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedSearchDeepLinkingView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showSRP((SearchParameters) obj);
    }
}
