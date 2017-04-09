package com.gumtree.android.vip_treebay;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$11 implements Provider {
    private static final VIPMainFragmentTreebay$$Lambda$11 instance = new VIPMainFragmentTreebay$$Lambda$11();

    private VIPMainFragmentTreebay$$Lambda$11() {
    }

    public static Provider lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object get() {
        return VIPPriceFragmentTreebay.newInstance();
    }
}
