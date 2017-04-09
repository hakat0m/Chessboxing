package com.gumtree.android.api.okhttp;

public interface OkHttpConfig {
    long getConnectTimeout();

    long getReadTimeout();

    long getWriteTimeout();
}
