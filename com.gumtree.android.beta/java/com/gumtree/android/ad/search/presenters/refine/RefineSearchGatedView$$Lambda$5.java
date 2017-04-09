package com.gumtree.android.ad.search.presenters.refine;

import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class RefineSearchGatedView$$Lambda$5 implements Action {
    private final View arg$1;

    private RefineSearchGatedView$$Lambda$5(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new RefineSearchGatedView$$Lambda$5(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.refineLocation();
    }
}
