package com.gumtree.android.location.service;

import android.location.Address;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class GeocoderLocationService$$Lambda$6 implements Func1 {
    private static final GeocoderLocationService$$Lambda$6 instance = new GeocoderLocationService$$Lambda$6();

    private GeocoderLocationService$$Lambda$6() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return GeocoderLocationService.lambda$askForCurrentLocationPostcode$5((Address) obj);
    }
}
