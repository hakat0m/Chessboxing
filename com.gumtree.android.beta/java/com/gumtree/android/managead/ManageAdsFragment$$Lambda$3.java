package com.gumtree.android.managead;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ManageAdsFragment$$Lambda$3 implements OnClickListener {
    private final ManageAdsFragment arg$1;
    private final long arg$2;

    private ManageAdsFragment$$Lambda$3(ManageAdsFragment manageAdsFragment, long j) {
        this.arg$1 = manageAdsFragment;
        this.arg$2 = j;
    }

    public static OnClickListener lambdaFactory$(ManageAdsFragment manageAdsFragment, long j) {
        return new ManageAdsFragment$$Lambda$3(manageAdsFragment, j);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$removeItem$2(this.arg$2, view);
    }
}
