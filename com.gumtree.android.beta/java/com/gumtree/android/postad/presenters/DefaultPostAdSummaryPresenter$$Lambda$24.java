package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$24 implements Func1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$24(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Func1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$24(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$connectSelectedFeaturesAndPriceInfo$25((List) obj);
    }
}
