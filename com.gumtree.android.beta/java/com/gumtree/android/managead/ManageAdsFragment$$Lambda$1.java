package com.gumtree.android.managead;

import com.gumtree.android.managead.ManageAdsItemAdapter.OnItemActionClick;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ManageAdsFragment$$Lambda$1 implements OnItemActionClick {
    private final ManageAdsFragment arg$1;

    private ManageAdsFragment$$Lambda$1(ManageAdsFragment manageAdsFragment) {
        this.arg$1 = manageAdsFragment;
    }

    public static OnItemActionClick lambdaFactory$(ManageAdsFragment manageAdsFragment) {
        return new ManageAdsFragment$$Lambda$1(manageAdsFragment);
    }

    @Hidden
    public void onAction(int i, long j) {
        this.arg$1.lambda$new$0(i, j);
    }
}
