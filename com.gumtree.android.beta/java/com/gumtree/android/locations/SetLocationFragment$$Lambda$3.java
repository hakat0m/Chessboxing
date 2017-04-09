package com.gumtree.android.locations;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SetLocationFragment$$Lambda$3 implements OnClickListener {
    private final SetLocationFragment arg$1;

    private SetLocationFragment$$Lambda$3(SetLocationFragment setLocationFragment) {
        this.arg$1 = setLocationFragment;
    }

    public static OnClickListener lambdaFactory$(SetLocationFragment setLocationFragment) {
        return new SetLocationFragment$$Lambda$3(setLocationFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$2(view);
    }
}
