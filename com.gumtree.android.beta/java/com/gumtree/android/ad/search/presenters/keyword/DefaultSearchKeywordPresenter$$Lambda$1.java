package com.gumtree.android.ad.search.presenters.keyword;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultSearchKeywordPresenter$$Lambda$1 implements Func1 {
    private static final DefaultSearchKeywordPresenter$$Lambda$1 instance = new DefaultSearchKeywordPresenter$$Lambda$1();

    private DefaultSearchKeywordPresenter$$Lambda$1() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultSearchKeywordPresenter.lambda$setupObservables$0((String) obj);
    }
}
