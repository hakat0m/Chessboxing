package com.gumtree.android.ad.search.presenters.keyword;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultSearchKeywordPresenter$$Lambda$2 implements Func1 {
    private final DefaultSearchKeywordPresenter arg$1;

    private DefaultSearchKeywordPresenter$$Lambda$2(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        this.arg$1 = defaultSearchKeywordPresenter;
    }

    public static Func1 lambdaFactory$(DefaultSearchKeywordPresenter defaultSearchKeywordPresenter) {
        return new DefaultSearchKeywordPresenter$$Lambda$2(defaultSearchKeywordPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$setupObservables$1((String) obj);
    }
}
