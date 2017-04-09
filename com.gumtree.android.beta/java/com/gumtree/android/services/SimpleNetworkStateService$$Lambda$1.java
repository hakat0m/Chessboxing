package com.gumtree.android.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class SimpleNetworkStateService$$Lambda$1 implements Action0 {
    private final Action0 arg$1;

    private SimpleNetworkStateService$$Lambda$1(Action0 action0) {
        this.arg$1 = action0;
    }

    public static Action0 lambdaFactory$(Action0 action0) {
        return new SimpleNetworkStateService$$Lambda$1(action0);
    }

    @Hidden
    public void call() {
        SimpleNetworkStateService.lambda$unsubscribeNetworkState$1(this.arg$1);
    }
}
