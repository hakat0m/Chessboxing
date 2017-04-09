package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$9 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$9 instance = new DefaultPostAdSummaryPresenter$$Lambda$9();

    private DefaultPostAdSummaryPresenter$$Lambda$9() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return DefaultPostAdSummaryPresenter.lambda$connectCancelMetadataLoad$8(obj, (Boolean) obj2);
    }
}
