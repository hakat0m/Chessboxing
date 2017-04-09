package com.gumtree.android.location.service;

import android.location.Location;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GeocoderLocationService$$Lambda$4 implements Action1 {
    private static final GeocoderLocationService$$Lambda$4 instance = new GeocoderLocationService$$Lambda$4();

    private GeocoderLocationService$$Lambda$4() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        GeocoderLocationService.lambda$askForCurrentLocationPostcode$3((Location) obj);
    }
}
