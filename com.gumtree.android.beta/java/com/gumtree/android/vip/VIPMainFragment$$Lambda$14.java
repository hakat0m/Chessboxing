package com.gumtree.android.vip;

import java.lang.invoke.LambdaForm.Hidden;
import javax.inject.Provider;

final /* synthetic */ class VIPMainFragment$$Lambda$14 implements Provider {
    private static final VIPMainFragment$$Lambda$14 instance = new VIPMainFragment$$Lambda$14();

    private VIPMainFragment$$Lambda$14() {
    }

    public static Provider lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object get() {
        return VIPTitleFragment.newInstance();
    }
}
