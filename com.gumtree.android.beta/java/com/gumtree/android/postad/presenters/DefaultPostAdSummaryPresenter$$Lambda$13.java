package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$13 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$13 instance = new DefaultPostAdSummaryPresenter$$Lambda$13();

    private DefaultPostAdSummaryPresenter$$Lambda$13() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$connectClearCategory$12((Boolean) obj);
    }
}
