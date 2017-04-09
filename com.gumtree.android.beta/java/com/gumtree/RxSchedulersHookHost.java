package com.gumtree;

import android.support.annotation.Nullable;
import rx.functions.Action0;
import rx.plugins.RxJavaSchedulersHook;

public class RxSchedulersHookHost extends RxJavaSchedulersHook {
    private static RxSchedulersHookHost instance;
    private RxJavaSchedulersHook delegate;

    public static synchronized RxSchedulersHookHost getInstance() {
        RxSchedulersHookHost rxSchedulersHookHost;
        synchronized (RxSchedulersHookHost.class) {
            if (instance == null) {
                instance = new RxSchedulersHookHost();
            }
            rxSchedulersHookHost = instance;
        }
        return rxSchedulersHookHost;
    }

    public Action0 onSchedule(Action0 action0) {
        RxJavaSchedulersHook delegate = getDelegate();
        return delegate == null ? super.onSchedule(action0) : delegate.onSchedule(action0);
    }

    private synchronized RxJavaSchedulersHook getDelegate() {
        return this.delegate;
    }

    public synchronized void setDelegate(@Nullable RxJavaSchedulersHook rxJavaSchedulersHook) {
        this.delegate = rxJavaSchedulersHook;
    }
}
