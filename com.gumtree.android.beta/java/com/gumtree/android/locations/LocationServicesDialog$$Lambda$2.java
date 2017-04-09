package com.gumtree.android.locations;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationServicesDialog$$Lambda$2 implements OnClickListener {
    private static final LocationServicesDialog$$Lambda$2 instance = new LocationServicesDialog$$Lambda$2();

    private LocationServicesDialog$$Lambda$2() {
    }

    public static OnClickListener lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        LocationServicesDialog.lambda$onCreateDialog$1(dialogInterface, i);
    }
}
