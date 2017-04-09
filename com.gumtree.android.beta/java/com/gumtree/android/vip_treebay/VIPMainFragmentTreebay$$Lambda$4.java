package com.gumtree.android.vip_treebay;

import com.gumtree.android.api.treebay.ItemSummary;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class VIPMainFragmentTreebay$$Lambda$4 implements Func1 {
    private static final VIPMainFragmentTreebay$$Lambda$4 instance = new VIPMainFragmentTreebay$$Lambda$4();

    private VIPMainFragmentTreebay$$Lambda$4() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return VIPMainFragmentTreebay.lambda$onCreateView$3((ItemSummary) obj);
    }
}
