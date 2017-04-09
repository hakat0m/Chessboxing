package com.gumtree.android.splash;

import android.support.annotation.Nullable;
import com.gumtree.android.mvp.Gate;
import com.gumtree.android.splash.presenter.SplashPresenter.View;
import rx.Subscription;
import rx.subjects.BehaviorSubject;

public class GatedSplashView implements View {
    private final Gate<Void> showSmartLockLoginFailure = new Gate();
    private final Subscription subscription = this.trigger.subscribe(GatedSplashView$$Lambda$1.lambdaFactory$(this));
    private final BehaviorSubject<View> trigger = BehaviorSubject.create();

    /* synthetic */ void lambda$new$0(View view) {
        if (view == null) {
            close();
        } else {
            open(view);
        }
    }

    private void open(View view) {
        this.showSmartLockLoginFailure.open(GatedSplashView$$Lambda$4.lambdaFactory$(view));
    }

    private void close() {
        this.showSmartLockLoginFailure.close();
    }

    public void setDecorated(@Nullable View view) {
        this.trigger.onNext(view);
    }

    public void showSmartLockLoginFailure() {
        this.showSmartLockLoginFailure.perform(null);
    }

    public void sealIt() {
        this.subscription.unsubscribe();
    }
}
