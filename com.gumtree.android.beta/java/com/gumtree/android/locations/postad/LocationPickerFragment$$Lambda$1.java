package com.gumtree.android.locations.postad;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationPickerFragment$$Lambda$1 implements OnClickListener {
    private final LocationPickerFragment arg$1;

    private LocationPickerFragment$$Lambda$1(LocationPickerFragment locationPickerFragment) {
        this.arg$1 = locationPickerFragment;
    }

    public static OnClickListener lambdaFactory$(LocationPickerFragment locationPickerFragment) {
        return new LocationPickerFragment$$Lambda$1(locationPickerFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$new$0(view);
    }
}
