package com.gumtree.android.vip;

import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.NestedScrollView.OnScrollChangeListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class VIPMainFragment$$Lambda$1 implements OnScrollChangeListener {
    private final VIPMainFragment arg$1;

    private VIPMainFragment$$Lambda$1(VIPMainFragment vIPMainFragment) {
        this.arg$1 = vIPMainFragment;
    }

    public static OnScrollChangeListener lambdaFactory$(VIPMainFragment vIPMainFragment) {
        return new VIPMainFragment$$Lambda$1(vIPMainFragment);
    }

    @Hidden
    public void onScrollChange(NestedScrollView nestedScrollView, int i, int i2, int i3, int i4) {
        this.arg$1.lambda$onCreateView$0(nestedScrollView, i, i2, i3, i4);
    }
}
