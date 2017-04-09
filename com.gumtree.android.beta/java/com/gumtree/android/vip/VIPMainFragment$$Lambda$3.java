package com.gumtree.android.vip;

import com.gumtree.android.vip.bing.BingAdFragment;
import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$3 implements Provider {
    private final long arg$1;

    private VIPMainFragment$$Lambda$3(long j) {
        this.arg$1 = j;
    }

    public static Provider lambdaFactory$(long j) {
        return new VIPMainFragment$$Lambda$3(j);
    }

    @Hidden
    public Object get() {
        return BingAdFragment.newInstance(this.arg$1);
    }
}
