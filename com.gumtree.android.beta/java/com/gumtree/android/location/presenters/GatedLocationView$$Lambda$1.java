package com.gumtree.android.location.presenters;

import com.gumtree.android.location.presenters.LocationPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedLocationView$$Lambda$1 implements Action1 {
    private final GatedLocationView arg$1;

    private GatedLocationView$$Lambda$1(GatedLocationView gatedLocationView) {
        this.arg$1 = gatedLocationView;
    }

    public static Action1 lambdaFactory$(GatedLocationView gatedLocationView) {
        return new GatedLocationView$$Lambda$1(gatedLocationView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
