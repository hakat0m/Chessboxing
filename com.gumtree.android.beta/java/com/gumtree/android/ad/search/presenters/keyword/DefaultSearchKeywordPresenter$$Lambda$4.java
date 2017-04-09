package com.gumtree.android.ad.search.presenters.keyword;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultSearchKeywordPresenter$$Lambda$4 implements Func1 {
    private static final DefaultSearchKeywordPresenter$$Lambda$4 instance = new DefaultSearchKeywordPresenter$$Lambda$4();

    private DefaultSearchKeywordPresenter$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultSearchKeywordPresenter.lambda$setupObservables$3((String) obj);
    }
}
