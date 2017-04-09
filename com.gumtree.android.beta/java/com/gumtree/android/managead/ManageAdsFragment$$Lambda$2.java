package com.gumtree.android.managead;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ManageAdsFragment$$Lambda$2 implements OnClickListener {
    private final ManageAdsFragment arg$1;

    private ManageAdsFragment$$Lambda$2(ManageAdsFragment manageAdsFragment) {
        this.arg$1 = manageAdsFragment;
    }

    public static OnClickListener lambdaFactory$(ManageAdsFragment manageAdsFragment) {
        return new ManageAdsFragment$$Lambda$2(manageAdsFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$1(view);
    }
}
