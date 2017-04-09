package com.gumtree.android.repositories;

import rx.Observable;

public interface Repository<T> {
    Observable<Void> clear(String str);

    Observable<T> load(String str);

    Observable<Void> save(T t);
}
