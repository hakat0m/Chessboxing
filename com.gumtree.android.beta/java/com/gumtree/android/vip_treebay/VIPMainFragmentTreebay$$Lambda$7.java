package com.gumtree.android.vip_treebay;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$7 implements Action1 {
    private final VIPMainFragmentTreebay arg$1;

    private VIPMainFragmentTreebay$$Lambda$7(VIPMainFragmentTreebay vIPMainFragmentTreebay) {
        this.arg$1 = vIPMainFragmentTreebay;
    }

    public static Action1 lambdaFactory$(VIPMainFragmentTreebay vIPMainFragmentTreebay) {
        return new VIPMainFragmentTreebay$$Lambda$7(vIPMainFragmentTreebay);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onCreateView$6((Throwable) obj);
    }
}
