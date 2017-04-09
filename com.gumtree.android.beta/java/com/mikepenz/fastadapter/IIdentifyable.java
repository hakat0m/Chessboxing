package com.mikepenz.fastadapter;

public interface IIdentifyable<T> {
    long getIdentifier();

    T withIdentifier(long j);
}
