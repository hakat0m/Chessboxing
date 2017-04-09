package com.gumtree.android.location.service;

import com.google.android.gms.common.api.GoogleApiClient;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GeocoderLocationService$$Lambda$3 implements Action1 {
    private static final GeocoderLocationService$$Lambda$3 instance = new GeocoderLocationService$$Lambda$3();

    private GeocoderLocationService$$Lambda$3() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        GeocoderLocationService.lambda$askForCurrentLocationPostcode$2((GoogleApiClient) obj);
    }
}
