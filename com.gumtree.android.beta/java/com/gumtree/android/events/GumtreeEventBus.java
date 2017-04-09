package com.gumtree.android.events;

import android.os.Handler;
import android.os.Looper;
import com.gumtree.android.common.utils.Log;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

public class GumtreeEventBus implements EventBus {
    private final Bus bus;
    private final Handler handler = new Handler(Looper.getMainLooper());

    public GumtreeEventBus(boolean z) {
        this.bus = z ? new Bus(ThreadEnforcer.ANY) : new Bus();
    }

    public void register(Object obj) {
        this.bus.register(obj);
    }

    public void unregister(Object obj) {
        try {
            this.bus.unregister(obj);
        } catch (Throwable e) {
            Log.e("Error when trying to unregister a bus", e);
        }
    }

    public void post(Object obj) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            this.bus.post(obj);
        } else {
            this.handler.post(GumtreeEventBus$$Lambda$1.lambdaFactory$(this, obj));
        }
    }

    /* synthetic */ void lambda$post$0(Object obj) {
        this.bus.post(obj);
    }
}
