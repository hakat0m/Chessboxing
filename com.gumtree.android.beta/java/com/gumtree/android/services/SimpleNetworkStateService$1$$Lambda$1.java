package com.gumtree.android.services;

import android.content.BroadcastReceiver;
import com.gumtree.android.services.SimpleNetworkStateService.1;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class SimpleNetworkStateService$1$$Lambda$1 implements Action0 {
    private final 1 arg$1;
    private final BroadcastReceiver arg$2;

    private SimpleNetworkStateService$1$$Lambda$1(1 1, BroadcastReceiver broadcastReceiver) {
        this.arg$1 = 1;
        this.arg$2 = broadcastReceiver;
    }

    public static Action0 lambdaFactory$(1 1, BroadcastReceiver broadcastReceiver) {
        return new SimpleNetworkStateService$1$$Lambda$1(1, broadcastReceiver);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$call$0(this.arg$2);
    }
}
