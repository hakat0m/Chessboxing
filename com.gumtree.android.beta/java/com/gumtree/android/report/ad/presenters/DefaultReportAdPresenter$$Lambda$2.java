package com.gumtree.android.report.ad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultReportAdPresenter$$Lambda$2 implements Action1 {
    private final DefaultReportAdPresenter arg$1;

    private DefaultReportAdPresenter$$Lambda$2(DefaultReportAdPresenter defaultReportAdPresenter) {
        this.arg$1 = defaultReportAdPresenter;
    }

    public static Action1 lambdaFactory$(DefaultReportAdPresenter defaultReportAdPresenter) {
        return new DefaultReportAdPresenter$$Lambda$2(defaultReportAdPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onSendReport$1((Throwable) obj);
    }
}
