package com.gumtree.android.report.ad.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedReportAdFragmentView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedReportAdFragmentView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedReportAdFragmentView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showSendButton(((Boolean) obj).booleanValue());
    }
}
