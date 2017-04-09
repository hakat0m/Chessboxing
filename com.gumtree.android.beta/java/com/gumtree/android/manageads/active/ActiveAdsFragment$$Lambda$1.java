package com.gumtree.android.manageads.active;

import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ActiveAdsFragment$$Lambda$1 implements OnRefreshListener {
    private final ActiveAdsFragment arg$1;

    private ActiveAdsFragment$$Lambda$1(ActiveAdsFragment activeAdsFragment) {
        this.arg$1 = activeAdsFragment;
    }

    public static OnRefreshListener lambdaFactory$(ActiveAdsFragment activeAdsFragment) {
        return new ActiveAdsFragment$$Lambda$1(activeAdsFragment);
    }

    @Hidden
    public void onRefresh() {
        this.arg$1.lambda$initUI$0();
    }
}
