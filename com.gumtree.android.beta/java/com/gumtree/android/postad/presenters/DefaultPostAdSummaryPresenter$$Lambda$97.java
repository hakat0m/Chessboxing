package com.gumtree.android.postad.presenters;

import java.io.File;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$97 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$97 instance = new DefaultPostAdSummaryPresenter$$Lambda$97();

    private DefaultPostAdSummaryPresenter$$Lambda$97() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return new File((String) obj);
    }
}
