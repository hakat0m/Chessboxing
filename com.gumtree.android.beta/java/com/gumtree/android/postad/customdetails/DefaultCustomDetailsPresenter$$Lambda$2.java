package com.gumtree.android.postad.customdetails;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultCustomDetailsPresenter$$Lambda$2 implements Action0 {
    private final DefaultCustomDetailsPresenter arg$1;

    private DefaultCustomDetailsPresenter$$Lambda$2(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        this.arg$1 = defaultCustomDetailsPresenter;
    }

    public static Action0 lambdaFactory$(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        return new DefaultCustomDetailsPresenter$$Lambda$2(defaultCustomDetailsPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$requestVRMLookup$1();
    }
}
