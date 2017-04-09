package com.gumtree.android.api;

public interface Retrofit2Client {
    <T> T api(Class<T> cls);
}
