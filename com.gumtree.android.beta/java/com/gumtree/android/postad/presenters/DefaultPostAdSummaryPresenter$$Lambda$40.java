package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$40 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$40 instance = new DefaultPostAdSummaryPresenter$$Lambda$40();

    private DefaultPostAdSummaryPresenter$$Lambda$40() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from(CollectionUtils.emptyIfNull((List) obj)).filter(DefaultPostAdSummaryPresenter$$Lambda$98.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$99.lambdaFactory$()).defaultIfEmpty(null);
    }
}
