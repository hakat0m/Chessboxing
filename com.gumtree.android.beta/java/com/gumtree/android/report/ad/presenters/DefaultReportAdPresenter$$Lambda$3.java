package com.gumtree.android.report.ad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Action1;

final /* synthetic */ class DefaultReportAdPresenter$$Lambda$3 implements Action1 {
    private final DefaultReportAdPresenter arg$1;

    private DefaultReportAdPresenter$$Lambda$3(DefaultReportAdPresenter defaultReportAdPresenter) {
        this.arg$1 = defaultReportAdPresenter;
    }

    public static Action1 lambdaFactory$(DefaultReportAdPresenter defaultReportAdPresenter) {
        return new DefaultReportAdPresenter$$Lambda$3(defaultReportAdPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onFetchReasons$2((List) obj);
    }
}
