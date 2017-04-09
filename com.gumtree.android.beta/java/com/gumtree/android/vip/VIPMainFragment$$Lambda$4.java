package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$4 implements Provider {
    private final long arg$1;

    private VIPMainFragment$$Lambda$4(long j) {
        this.arg$1 = j;
    }

    public static Provider lambdaFactory$(long j) {
        return new VIPMainFragment$$Lambda$4(j);
    }

    @Hidden
    public Object get() {
        return VIPTextLinkFragment.newInstance(this.arg$1);
    }
}
