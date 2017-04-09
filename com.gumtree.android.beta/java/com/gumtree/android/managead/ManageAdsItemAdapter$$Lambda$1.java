package com.gumtree.android.managead;

import android.view.View;
import com.gumtree.android.managead.ExpandableCursorListAdapter.OnExpandableButtonToggleListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class ManageAdsItemAdapter$$Lambda$1 implements OnExpandableButtonToggleListener {
    private final ManageAdsItemAdapter arg$1;

    private ManageAdsItemAdapter$$Lambda$1(ManageAdsItemAdapter manageAdsItemAdapter) {
        this.arg$1 = manageAdsItemAdapter;
    }

    public static OnExpandableButtonToggleListener lambdaFactory$(ManageAdsItemAdapter manageAdsItemAdapter) {
        return new ManageAdsItemAdapter$$Lambda$1(manageAdsItemAdapter);
    }

    @Hidden
    public void onExpandedViewToggled(View view, boolean z) {
        this.arg$1.lambda$new$0(view, z);
    }
}
