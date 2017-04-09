package com.gumtree.android.location.service;

import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class GeocoderLocationService$$Lambda$2 implements Func1 {
    private final GeocoderLocationService arg$1;

    private GeocoderLocationService$$Lambda$2(GeocoderLocationService geocoderLocationService) {
        this.arg$1 = geocoderLocationService;
    }

    public static Func1 lambdaFactory$(GeocoderLocationService geocoderLocationService) {
        return new GeocoderLocationService$$Lambda$2(geocoderLocationService);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$askForCurrentLocationPostcode$1((GoogleApiClient) obj);
    }
}
