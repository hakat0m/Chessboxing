package com.gumtree.android.postad.customdetails;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultCustomDetailsPresenter$$Lambda$5 implements Action1 {
    private final DefaultCustomDetailsPresenter arg$1;

    private DefaultCustomDetailsPresenter$$Lambda$5(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        this.arg$1 = defaultCustomDetailsPresenter;
    }

    public static Action1 lambdaFactory$(DefaultCustomDetailsPresenter defaultCustomDetailsPresenter) {
        return new DefaultCustomDetailsPresenter$$Lambda$5(defaultCustomDetailsPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$requestVRMLookup$4((Throwable) obj);
    }
}
