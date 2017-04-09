package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$81 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$81 instance = new DefaultPostAdSummaryPresenter$$Lambda$81();

    private DefaultPostAdSummaryPresenter$$Lambda$81() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return PostAdSummaryUtils.asURL((String) obj);
    }
}
