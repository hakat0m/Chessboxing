package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$11 implements Provider {
    private static final VIPMainFragment$$Lambda$11 instance = new VIPMainFragment$$Lambda$11();

    private VIPMainFragment$$Lambda$11() {
    }

    public static Provider lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object get() {
        return VIPSellerOtherItemsFragment.newInstance();
    }
}
