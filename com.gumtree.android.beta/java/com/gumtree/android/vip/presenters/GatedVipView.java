package com.gumtree.android.vip.presenters;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.vip.presenters.VipPresenter.View;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedVipView implements View {
    private Gate<String> showDialDialog = new Gate();
    private Gate<Boolean> showSpinnerCall = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedVipView$$Lambda$1.lambdaFactory$(this));
    private BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    void sealIt() {
        this.subscription.unsubscribe();
    }

    private void open(View view) {
        this.showDialDialog.open(GatedVipView$$Lambda$4.lambdaFactory$(view));
        this.showSpinnerCall.open(GatedVipView$$Lambda$5.lambdaFactory$(view));
    }

    private void close() {
        this.showDialDialog.close();
        this.showSpinnerCall.close();
    }

    public void showPhoneDial(String str) {
        this.showDialDialog.perform(str);
    }

    public void showSpinnerCallButton(boolean z) {
        this.showSpinnerCall.perform(Boolean.valueOf(z));
    }
}
