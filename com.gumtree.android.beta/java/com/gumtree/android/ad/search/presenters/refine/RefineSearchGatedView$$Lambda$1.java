package com.gumtree.android.ad.search.presenters.refine;

import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class RefineSearchGatedView$$Lambda$1 implements Action1 {
    private final RefineSearchGatedView arg$1;

    private RefineSearchGatedView$$Lambda$1(RefineSearchGatedView refineSearchGatedView) {
        this.arg$1 = refineSearchGatedView;
    }

    public static Action1 lambdaFactory$(RefineSearchGatedView refineSearchGatedView) {
        return new RefineSearchGatedView$$Lambda$1(refineSearchGatedView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
