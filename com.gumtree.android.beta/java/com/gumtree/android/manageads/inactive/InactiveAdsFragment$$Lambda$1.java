package com.gumtree.android.manageads.inactive;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class InactiveAdsFragment$$Lambda$1 implements OnRefreshListener {
    private final InactiveAdsFragment arg$1;

    private InactiveAdsFragment$$Lambda$1(InactiveAdsFragment inactiveAdsFragment) {
        this.arg$1 = inactiveAdsFragment;
    }

    public static OnRefreshListener lambdaFactory$(InactiveAdsFragment inactiveAdsFragment) {
        return new InactiveAdsFragment$$Lambda$1(inactiveAdsFragment);
    }

    @Hidden
    public void onRefresh() {
        this.arg$1.lambda$initUI$0();
    }
}
