package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$10 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$10 instance = new DefaultPostAdSummaryPresenter$$Lambda$10();

    private DefaultPostAdSummaryPresenter$$Lambda$10() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$connectCancelMetadataLoad$9((Boolean) obj);
    }
}
