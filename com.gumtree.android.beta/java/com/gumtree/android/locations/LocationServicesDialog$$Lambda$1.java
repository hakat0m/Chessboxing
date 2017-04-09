package com.gumtree.android.locations;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class LocationServicesDialog$$Lambda$1 implements OnClickListener {
    private final LocationServicesDialog arg$1;

    private LocationServicesDialog$$Lambda$1(LocationServicesDialog locationServicesDialog) {
        this.arg$1 = locationServicesDialog;
    }

    public static OnClickListener lambdaFactory$(LocationServicesDialog locationServicesDialog) {
        return new LocationServicesDialog$$Lambda$1(locationServicesDialog);
    }

    @Hidden
    public void onClick(DialogInterface dialogInterface, int i) {
        this.arg$1.lambda$onCreateDialog$0(dialogInterface, i);
    }
}
