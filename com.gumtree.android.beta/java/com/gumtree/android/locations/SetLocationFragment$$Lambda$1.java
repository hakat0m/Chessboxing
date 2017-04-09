package com.gumtree.android.locations;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class SetLocationFragment$$Lambda$1 implements OnClickListener {
    private final SetLocationFragment arg$1;

    private SetLocationFragment$$Lambda$1(SetLocationFragment setLocationFragment) {
        this.arg$1 = setLocationFragment;
    }

    public static OnClickListener lambdaFactory$(SetLocationFragment setLocationFragment) {
        return new SetLocationFragment$$Lambda$1(setLocationFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(view);
    }
}
