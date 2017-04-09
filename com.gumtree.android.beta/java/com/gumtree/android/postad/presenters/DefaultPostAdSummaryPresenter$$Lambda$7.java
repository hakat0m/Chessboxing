package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$7 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$7 instance = new DefaultPostAdSummaryPresenter$$Lambda$7();

    private DefaultPostAdSummaryPresenter$$Lambda$7() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return DefaultPostAdSummaryPresenter.lambda$connectRequestCloseView$6(obj, (Boolean) obj2);
    }
}
