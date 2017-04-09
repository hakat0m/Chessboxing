package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$8 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$8 instance = new DefaultPostAdSummaryPresenter$$Lambda$8();

    private DefaultPostAdSummaryPresenter$$Lambda$8() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$connectRequestCloseView$7((Boolean) obj);
    }
}
