package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$6 implements Provider {
    private final long arg$1;

    private VIPMainFragment$$Lambda$6(long j) {
        this.arg$1 = j;
    }

    public static Provider lambdaFactory$(long j) {
        return new VIPMainFragment$$Lambda$6(j);
    }

    @Hidden
    public Object get() {
        return VIPReportAdFragment.newInstance(this.arg$1);
    }
}
