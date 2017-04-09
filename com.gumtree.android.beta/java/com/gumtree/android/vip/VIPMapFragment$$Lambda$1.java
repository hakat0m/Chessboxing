package com.gumtree.android.vip;

import com.google.android.gms.maps.GoogleMap.OnMapClickListener;
import com.google.android.gms.maps.model.LatLng;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPMapFragment$$Lambda$1 implements OnMapClickListener {
    private final VIPMapFragment arg$1;

    private VIPMapFragment$$Lambda$1(VIPMapFragment vIPMapFragment) {
        this.arg$1 = vIPMapFragment;
    }

    public static OnMapClickListener lambdaFactory$(VIPMapFragment vIPMapFragment) {
        return new VIPMapFragment$$Lambda$1(vIPMapFragment);
    }

    @Hidden
    public void onMapClick(LatLng latLng) {
        this.arg$1.lambda$onMapReady$0(latLng);
    }
}
