package com.gumtree.android.locations;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SetLocationActivity$$Lambda$1 implements OnClickListener {
    private final SetLocationActivity arg$1;

    private SetLocationActivity$$Lambda$1(SetLocationActivity setLocationActivity) {
        this.arg$1 = setLocationActivity;
    }

    public static OnClickListener lambdaFactory$(SetLocationActivity setLocationActivity) {
        return new SetLocationActivity$$Lambda$1(setLocationActivity);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreate$0(view);
    }
}
