package com.gumtree.android.ad.search.presenters.keyword;

import com.gumtree.android.ad.search.models.SuggestionItem;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SearchKeywordGatedView$$Lambda$3 implements Action {
    private final View arg$1;

    private SearchKeywordGatedView$$Lambda$3(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new SearchKeywordGatedView$$Lambda$3(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.openSearchRefine((SuggestionItem) obj);
    }
}
