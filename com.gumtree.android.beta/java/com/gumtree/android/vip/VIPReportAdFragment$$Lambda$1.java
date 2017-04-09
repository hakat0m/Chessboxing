package com.gumtree.android.vip;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPReportAdFragment$$Lambda$1 implements OnClickListener {
    private final VIPReportAdFragment arg$1;
    private final long arg$2;

    private VIPReportAdFragment$$Lambda$1(VIPReportAdFragment vIPReportAdFragment, long j) {
        this.arg$1 = vIPReportAdFragment;
        this.arg$2 = j;
    }

    public static OnClickListener lambdaFactory$(VIPReportAdFragment vIPReportAdFragment, long j) {
        return new VIPReportAdFragment$$Lambda$1(vIPReportAdFragment, j);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onViewCreated$0(this.arg$2, view);
    }
}
