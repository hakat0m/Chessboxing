package com.gumtree.android.postad.customdetails;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultCustomDetailsPresenter$$Lambda$6 implements Action0 {
    private final DefaultCustomDetailsPresenter arg$1;

    private DefaultCustomDetailsPresenter$$Lambda$6(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        this.arg$1 = defaultCustomDetailsPresenter;
    }

    public static Action0 lambdaFactory$(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        return new DefaultCustomDetailsPresenter$$Lambda$6(defaultCustomDetailsPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$requestVRMLookup$5();
    }
}
