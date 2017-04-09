package com.gumtree.android.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.Scheduler.Worker;
import rx.functions.Action0;

final /* synthetic */ class SimpleNetworkStateService$$Lambda$2 implements Action0 {
    private final Action0 arg$1;
    private final Worker arg$2;

    private SimpleNetworkStateService$$Lambda$2(Action0 action0, Worker worker) {
        this.arg$1 = action0;
        this.arg$2 = worker;
    }

    public static Action0 lambdaFactory$(Action0 action0, Worker worker) {
        return new SimpleNetworkStateService$$Lambda$2(action0, worker);
    }

    @Hidden
    public void call() {
        SimpleNetworkStateService.lambda$null$0(this.arg$1, this.arg$2);
    }
}
