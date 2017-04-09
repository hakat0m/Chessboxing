package com.gumtree.android.postad.payment;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.postad.payment.PostAdPaymentPresenter.View;
import org.apache.commons.lang3.tuple.Pair;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedPostAdPaymentView implements View {
    private final Gate<Void> exitScreen = new Gate();
    private final Gate<Void> showAbortDialog = new Gate();
    private final Gate<Void> showConfirmationDialog = new Gate();
    private final Gate<String> showError = new Gate();
    private final Gate<Boolean> showLoading = new Gate();
    private final Gate<Pair<String, Boolean>> showPayPalWebView = new Gate();
    private final Gate<Void> showPaymentIncludedInPackage = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedPostAdPaymentView$$Lambda$1.lambdaFactory$(this));
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
        this.showPayPalWebView.close();
        this.showConfirmationDialog.close();
        this.showAbortDialog.close();
        this.exitScreen.close();
        this.showPaymentIncludedInPackage.close();
        this.showLoading.close();
        this.showError.close();
    }

    private void open(View view) {
        this.showPayPalWebView.open(GatedPostAdPaymentView$$Lambda$2.lambdaFactory$(view));
        this.showConfirmationDialog.open(GatedPostAdPaymentView$$Lambda$3.lambdaFactory$(view));
        this.showAbortDialog.open(GatedPostAdPaymentView$$Lambda$4.lambdaFactory$(view));
        this.exitScreen.open(GatedPostAdPaymentView$$Lambda$5.lambdaFactory$(view));
        this.showPaymentIncludedInPackage.open(GatedPostAdPaymentView$$Lambda$6.lambdaFactory$(view));
        this.showLoading.open(GatedPostAdPaymentView$$Lambda$7.lambdaFactory$(view));
        this.showError.open(GatedPostAdPaymentView$$Lambda$8.lambdaFactory$(view));
    }

    public void showPayPalWebView(String str, boolean z) {
        this.showPayPalWebView.perform(Pair.of(str, Boolean.valueOf(z)));
    }

    public void showPaymentSuccessful() {
        this.showConfirmationDialog.perform(null);
    }

    public void showPaymentIncludedInPackage() {
        this.showPaymentIncludedInPackage.perform(null);
    }

    public void showAbortDialog() {
        this.showAbortDialog.perform(null);
    }

    public void showLoading(boolean z) {
        this.showLoading.perform(Boolean.valueOf(z));
    }

    public void showError(String str) {
        this.showError.perform(str);
    }

    public void openManageAds() {
        this.exitScreen.perform(null);
    }
}
