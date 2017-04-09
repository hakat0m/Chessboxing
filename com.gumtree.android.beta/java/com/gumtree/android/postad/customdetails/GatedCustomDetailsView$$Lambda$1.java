package com.gumtree.android.postad.customdetails;

import com.gumtree.android.postad.customdetails.CustomDetailsPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedCustomDetailsView$$Lambda$1 implements Action1 {
    private final GatedCustomDetailsView arg$1;

    private GatedCustomDetailsView$$Lambda$1(GatedCustomDetailsView gatedCustomDetailsView) {
        this.arg$1 = gatedCustomDetailsView;
    }

    public static Action1 lambdaFactory$(GatedCustomDetailsView gatedCustomDetailsView) {
        return new GatedCustomDetailsView$$Lambda$1(gatedCustomDetailsView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
