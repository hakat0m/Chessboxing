package com.gumtree.android.ad.search.presenters.keyword;

import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;

final /* synthetic */ class SearchKeywordGatedView$$Lambda$2 implements Action {
    private final View arg$1;

    private SearchKeywordGatedView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new SearchKeywordGatedView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showKeywordSuggestions((List) obj);
    }
}
