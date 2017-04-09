package com.gumtree.android.location.service;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class GeocoderLocationService$$Lambda$5 implements Func1 {
    private final GeocoderLocationService arg$1;

    private GeocoderLocationService$$Lambda$5(GeocoderLocationService geocoderLocationService) {
        this.arg$1 = geocoderLocationService;
    }

    public static Func1 lambdaFactory$(GeocoderLocationService geocoderLocationService) {
        return new GeocoderLocationService$$Lambda$5(geocoderLocationService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$askForCurrentLocationPostcode$4((Location) obj);
    }
}
