package com.gumtree.android.srp;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class NearByAdvertAdapter$$Lambda$1 implements OnClickListener {
    private final NearByAdvertAdapter arg$1;

    private NearByAdvertAdapter$$Lambda$1(NearByAdvertAdapter nearByAdvertAdapter) {
        this.arg$1 = nearByAdvertAdapter;
    }

    public static OnClickListener lambdaFactory$(NearByAdvertAdapter nearByAdvertAdapter) {
        return new NearByAdvertAdapter$$Lambda$1(nearByAdvertAdapter);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$setFooterView$0(view);
    }
}
