package com.gumtree.android.ad.search.presenters.keyword;

import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class SearchKeywordGatedView$$Lambda$1 implements Action1 {
    private final SearchKeywordGatedView arg$1;

    private SearchKeywordGatedView$$Lambda$1(SearchKeywordGatedView searchKeywordGatedView) {
        this.arg$1 = searchKeywordGatedView;
    }

    public static Action1 lambdaFactory$(SearchKeywordGatedView searchKeywordGatedView) {
        return new SearchKeywordGatedView$$Lambda$1(searchKeywordGatedView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
