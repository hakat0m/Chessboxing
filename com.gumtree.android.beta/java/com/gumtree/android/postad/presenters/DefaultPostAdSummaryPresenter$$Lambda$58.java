package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$58 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$58 instance = new DefaultPostAdSummaryPresenter$$Lambda$58();

    private DefaultPostAdSummaryPresenter$$Lambda$58() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return DefaultPostAdSummaryPresenter.lambda$connectEnablePosting$64((Boolean) obj, (Boolean) obj2);
    }
}
