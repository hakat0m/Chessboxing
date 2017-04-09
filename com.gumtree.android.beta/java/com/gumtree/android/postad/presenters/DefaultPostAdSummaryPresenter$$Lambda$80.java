package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$80 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$80 instance = new DefaultPostAdSummaryPresenter$$Lambda$80();

    private DefaultPostAdSummaryPresenter$$Lambda$80() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$downloadAdImages$86((String) obj);
    }
}
