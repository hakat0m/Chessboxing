package com.gumtree.android.postad.contactdetails;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdContactDetailsView$$Lambda$14 implements Action {
    private final View arg$1;

    private GatedPostAdContactDetailsView$$Lambda$14(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdContactDetailsView$$Lambda$14(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showPhoneError((String) obj);
    }
}
