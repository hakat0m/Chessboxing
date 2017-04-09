package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.net.URL;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$82 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$82 instance = new DefaultPostAdSummaryPresenter$$Lambda$82();

    private DefaultPostAdSummaryPresenter$$Lambda$82() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$downloadAdImages$88((URL) obj);
    }
}
