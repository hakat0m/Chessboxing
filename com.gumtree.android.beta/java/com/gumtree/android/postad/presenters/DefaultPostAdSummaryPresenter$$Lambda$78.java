package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$78 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$78 instance = new DefaultPostAdSummaryPresenter$$Lambda$78();

    private DefaultPostAdSummaryPresenter$$Lambda$78() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from((List) obj);
    }
}
