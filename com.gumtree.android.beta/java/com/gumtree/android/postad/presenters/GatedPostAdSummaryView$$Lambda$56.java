package com.gumtree.android.postad.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdSummaryView$$Lambda$56 implements Action {
    private final View arg$1;

    private GatedPostAdSummaryView$$Lambda$56(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdSummaryView$$Lambda$56(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.resetViews();
    }
}
