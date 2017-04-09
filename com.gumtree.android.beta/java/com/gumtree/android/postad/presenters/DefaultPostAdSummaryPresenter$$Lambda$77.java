package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$77 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$77 instance = new DefaultPostAdSummaryPresenter$$Lambda$77();

    private DefaultPostAdSummaryPresenter$$Lambda$77() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$downloadAdImages$83((List) obj);
    }
}
