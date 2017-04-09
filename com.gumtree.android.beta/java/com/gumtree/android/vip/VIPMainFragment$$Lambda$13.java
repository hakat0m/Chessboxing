package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$13 implements Provider {
    private static final VIPMainFragment$$Lambda$13 instance = new VIPMainFragment$$Lambda$13();

    private VIPMainFragment$$Lambda$13() {
    }

    public static Provider lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object get() {
        return VIPPriceFragment.newInstance();
    }
}
