package com.gumtree.android.ad.search.presenters.results;

import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedSearchResultsView$$Lambda$1 implements Action1 {
    private final GatedSearchResultsView arg$1;

    private GatedSearchResultsView$$Lambda$1(GatedSearchResultsView gatedSearchResultsView) {
        this.arg$1 = gatedSearchResultsView;
    }

    public static Action1 lambdaFactory$(GatedSearchResultsView gatedSearchResultsView) {
        return new GatedSearchResultsView$$Lambda$1(gatedSearchResultsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
