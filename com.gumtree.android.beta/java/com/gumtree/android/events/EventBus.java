package com.gumtree.android.events;

public interface EventBus {
    void post(Object obj);

    void register(Object obj);

    void unregister(Object obj);
}
