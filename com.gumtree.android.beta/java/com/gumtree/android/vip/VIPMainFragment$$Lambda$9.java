package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$9 implements Provider {
    private final VIPMainFragment arg$1;
    private final long arg$2;

    private VIPMainFragment$$Lambda$9(VIPMainFragment vIPMainFragment, long j) {
        this.arg$1 = vIPMainFragment;
        this.arg$2 = j;
    }

    public static Provider lambdaFactory$(VIPMainFragment vIPMainFragment, long j) {
        return new VIPMainFragment$$Lambda$9(vIPMainFragment, j);
    }

    @Hidden
    public Object get() {
        return this.arg$1.lambda$addDescription$8(this.arg$2);
    }
}
