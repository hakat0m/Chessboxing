package com.gumtree.android.vip.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.vip.presenters.VipPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedVipView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedVipView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedVipView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPhoneDial((String) obj);
    }
}
