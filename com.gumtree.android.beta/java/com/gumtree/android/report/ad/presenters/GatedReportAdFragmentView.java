package com.gumtree.android.report.ad.presenters;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.report.ad.ReportAdReason;
import com.gumtree.android.report.ad.ReportAdResponse;
import com.gumtree.android.report.ad.presenters.ReportAdPresenter.View;
import java.util.List;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedReportAdFragmentView implements View {
    private Gate<String> showError = new Gate();
    private Gate<List<ReportAdReason>> showReasons = new Gate();
    private Gate<ReportAdResponse> showReportSent = new Gate();
    private Gate<Boolean> showSendButton = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedReportAdFragmentView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }

    private void close() {
        this.showSendButton.close();
        this.showReasons.close();
        this.showReportSent.close();
        this.showError.close();
    }

    private void open(View view) {
        this.showSendButton.open(GatedReportAdFragmentView$$Lambda$4.lambdaFactory$(view));
        this.showReasons.open(GatedReportAdFragmentView$$Lambda$5.lambdaFactory$(view));
        this.showReportSent.open(GatedReportAdFragmentView$$Lambda$6.lambdaFactory$(view));
        this.showError.open(GatedReportAdFragmentView$$Lambda$7.lambdaFactory$(view));
    }

    public void showReasons(List<ReportAdReason> list) {
        this.showReasons.perform(list);
    }

    public void showSendButton(boolean z) {
        this.showSendButton.perform(Boolean.valueOf(z));
    }

    public void showReportSent(ReportAdResponse reportAdResponse) {
        this.showReportSent.perform(reportAdResponse);
    }

    public void showError(String str) {
        this.showError.perform(str);
    }
}
