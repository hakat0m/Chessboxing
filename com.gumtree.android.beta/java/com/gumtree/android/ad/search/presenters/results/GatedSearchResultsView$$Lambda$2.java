package com.gumtree.android.ad.search.presenters.results;

import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedSearchResultsView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedSearchResultsView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedSearchResultsView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showMessage((String) obj);
    }
}
