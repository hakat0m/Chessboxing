package com.gumtree.android.location.services;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationServiceDispatcher$$Lambda$1 implements Func1 {
    private final LocationServiceDispatcher arg$1;

    private LocationServiceDispatcher$$Lambda$1(LocationServiceDispatcher locationServiceDispatcher) {
        this.arg$1 = locationServiceDispatcher;
    }

    public static Func1 lambdaFactory$(LocationServiceDispatcher locationServiceDispatcher) {
        return new LocationServiceDispatcher$$Lambda$1(locationServiceDispatcher);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$getCurrentLocation$1((String) obj);
    }
}
