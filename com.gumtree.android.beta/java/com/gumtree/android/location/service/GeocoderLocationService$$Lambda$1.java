package com.gumtree.android.location.service;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func0;

final /* synthetic */ class GeocoderLocationService$$Lambda$1 implements Func0 {
    private final GeocoderLocationService arg$1;

    private GeocoderLocationService$$Lambda$1(GeocoderLocationService geocoderLocationService) {
        this.arg$1 = geocoderLocationService;
    }

    public static Func0 lambdaFactory$(GeocoderLocationService geocoderLocationService) {
        return new GeocoderLocationService$$Lambda$1(geocoderLocationService);
    }

    @Hidden
    public Object call() {
        return this.arg$1.lambda$askForCurrentLocationPostcode$0();
    }
}
