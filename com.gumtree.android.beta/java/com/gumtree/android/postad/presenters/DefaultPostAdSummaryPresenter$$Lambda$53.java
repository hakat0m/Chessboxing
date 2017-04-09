package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$53 implements Action1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$53(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$53(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$connectRequestFeatures$59((Pair) obj);
    }
}
