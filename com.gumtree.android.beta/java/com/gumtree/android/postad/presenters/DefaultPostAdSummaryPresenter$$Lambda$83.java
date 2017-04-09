package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$83 implements Action0 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$83(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Action0 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$83(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$downloadAdImages$89();
    }
}
