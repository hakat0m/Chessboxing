package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import org.apache.commons.collections4.CollectionUtils;
import rx.Observable;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$19 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$19 instance = new DefaultPostAdSummaryPresenter$$Lambda$19();

    private DefaultPostAdSummaryPresenter$$Lambda$19() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return Observable.from(CollectionUtils.emptyIfNull((List) obj)).filter(DefaultPostAdSummaryPresenter$$Lambda$101.lambdaFactory$()).map(DefaultPostAdSummaryPresenter$$Lambda$102.lambdaFactory$());
    }
}
