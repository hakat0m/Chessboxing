package com.gumtree.android.report.ad.presenters;

import com.gumtree.android.report.ad.presenters.ReportAdPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedReportAdFragmentView$$Lambda$1 implements Action1 {
    private final GatedReportAdFragmentView arg$1;

    private GatedReportAdFragmentView$$Lambda$1(GatedReportAdFragmentView gatedReportAdFragmentView) {
        this.arg$1 = gatedReportAdFragmentView;
    }

    public static Action1 lambdaFactory$(GatedReportAdFragmentView gatedReportAdFragmentView) {
        return new GatedReportAdFragmentView$$Lambda$1(gatedReportAdFragmentView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
