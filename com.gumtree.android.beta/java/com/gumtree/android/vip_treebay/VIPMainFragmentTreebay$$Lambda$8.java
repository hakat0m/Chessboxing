package com.gumtree.android.vip_treebay;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$8 implements Provider {
    private final long arg$1;

    private VIPMainFragmentTreebay$$Lambda$8(long j) {
        this.arg$1 = j;
    }

    public static Provider lambdaFactory$(long j) {
        return new VIPMainFragmentTreebay$$Lambda$8(j);
    }

    @Hidden
    public Object get() {
        return BingAdFragmentTreebay.newInstance(this.arg$1);
    }
}
