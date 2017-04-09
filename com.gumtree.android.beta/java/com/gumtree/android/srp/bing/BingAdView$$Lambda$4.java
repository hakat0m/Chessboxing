package com.gumtree.android.srp.bing;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class BingAdView$$Lambda$4 implements OnClickListener {
    private final BingAdView arg$1;

    private BingAdView$$Lambda$4(BingAdView bingAdView) {
        this.arg$1 = bingAdView;
    }

    public static OnClickListener lambdaFactory$(BingAdView bingAdView) {
        return new BingAdView$$Lambda$4(bingAdView);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$1(view);
    }
}
