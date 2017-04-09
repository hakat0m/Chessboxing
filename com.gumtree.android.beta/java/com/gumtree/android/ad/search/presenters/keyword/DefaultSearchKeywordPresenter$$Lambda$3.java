package com.gumtree.android.ad.search.presenters.keyword;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultSearchKeywordPresenter$$Lambda$3 implements Action1 {
    private final DefaultSearchKeywordPresenter arg$1;

    private DefaultSearchKeywordPresenter$$Lambda$3(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        this.arg$1 = defaultSearchKeywordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        return new DefaultSearchKeywordPresenter$$Lambda$3(defaultSearchKeywordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$setupObservables$2((List) obj);
    }
}
