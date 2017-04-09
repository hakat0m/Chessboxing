package com.gumtree.android.locations;

import android.view.View;
import android.view.View.OnClickListener;
import com.ebay.classifieds.capi.locations.Location;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationActivity$$Lambda$1 implements OnClickListener {
    private final LocationActivity arg$1;
    private final Location arg$2;

    private LocationActivity$$Lambda$1(LocationActivity locationActivity, Location location) {
        this.arg$1 = locationActivity;
        this.arg$2 = location;
    }

    public static OnClickListener lambdaFactory$(LocationActivity locationActivity, Location location) {
        return new LocationActivity$$Lambda$1(locationActivity, location);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$addLocationFragment$0(this.arg$2, view);
    }
}
