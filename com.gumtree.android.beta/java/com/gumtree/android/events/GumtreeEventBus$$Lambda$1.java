package com.gumtree.android.events;

import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GumtreeEventBus$$Lambda$1 implements Runnable {
    private final GumtreeEventBus arg$1;
    private final Object arg$2;

    private GumtreeEventBus$$Lambda$1(GumtreeEventBus gumtreeEventBus, Object obj) {
        this.arg$1 = gumtreeEventBus;
        this.arg$2 = obj;
    }

    public static Runnable lambdaFactory$(GumtreeEventBus gumtreeEventBus, Object obj) {
        return new GumtreeEventBus$$Lambda$1(gumtreeEventBus, obj);
    }

    @Hidden
    public void run() {
        this.arg$1.lambda$post$0(this.arg$2);
    }
}
