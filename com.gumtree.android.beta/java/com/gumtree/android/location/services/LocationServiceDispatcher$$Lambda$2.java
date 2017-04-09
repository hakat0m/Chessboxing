package com.gumtree.android.location.services;

import com.ebay.classifieds.capi.locations.Locations;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class LocationServiceDispatcher$$Lambda$2 implements Func1 {
    private final LocationServiceDispatcher arg$1;
    private final String arg$2;

    private LocationServiceDispatcher$$Lambda$2(LocationServiceDispatcher locationServiceDispatcher, String str) {
        this.arg$1 = locationServiceDispatcher;
        this.arg$2 = str;
    }

    public static Func1 lambdaFactory$(LocationServiceDispatcher locationServiceDispatcher, String str) {
        return new LocationServiceDispatcher$$Lambda$2(locationServiceDispatcher, str);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$null$0(this.arg$2, (Locations) obj);
    }
}
