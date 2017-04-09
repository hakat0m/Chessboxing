package com.gumtree.android.home;

import android.view.View;
import android.view.View.OnClickListener;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class HomeMainFragment$$Lambda$1 implements OnClickListener {
    private final HomeMainFragment arg$1;

    private HomeMainFragment$$Lambda$1(HomeMainFragment homeMainFragment) {
        this.arg$1 = homeMainFragment;
    }

    public static OnClickListener lambdaFactory$(HomeMainFragment homeMainFragment) {
        return new HomeMainFragment$$Lambda$1(homeMainFragment);
    }

    @Hidden
    public void onClick(View view) {
        this.arg$1.lambda$onCreateView$0(view);
    }
}
