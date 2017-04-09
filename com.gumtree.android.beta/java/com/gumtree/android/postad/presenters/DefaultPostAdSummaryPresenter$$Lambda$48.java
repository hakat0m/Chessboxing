package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Triple;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$48 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$48 instance = new DefaultPostAdSummaryPresenter$$Lambda$48();

    private DefaultPostAdSummaryPresenter$$Lambda$48() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$connectCategoryChanged$54((Triple) obj);
    }
}
