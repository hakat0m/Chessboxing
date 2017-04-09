package com.gumtree.android.location.presenters;

import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.location.presenters.LocationPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedLocationView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedLocationView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedLocationView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.completeWithLocation((LocationData) obj);
    }
}
