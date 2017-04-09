package com.gumtree.android.ad.search.presenters.keyword;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultSearchKeywordPresenter$$Lambda$5 implements Action1 {
    private final DefaultSearchKeywordPresenter arg$1;

    private DefaultSearchKeywordPresenter$$Lambda$5(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        this.arg$1 = defaultSearchKeywordPresenter;
    }

    public static Action1 lambdaFactory$(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        return new DefaultSearchKeywordPresenter$$Lambda$5(defaultSearchKeywordPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$setupObservables$4((String) obj);
    }
}
