package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$8 implements Provider {
    private final long arg$1;

    private VIPMainFragment$$Lambda$8(long j) {
        this.arg$1 = j;
    }

    public static Provider lambdaFactory$(long j) {
        return new VIPMainFragment$$Lambda$8(j);
    }

    @Hidden
    public Object get() {
        return VIPMapFragment.newInstance(this.arg$1);
    }
}
