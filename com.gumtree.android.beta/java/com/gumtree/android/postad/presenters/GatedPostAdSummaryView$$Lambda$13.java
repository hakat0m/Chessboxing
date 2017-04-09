package com.gumtree.android.postad.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;

final /* synthetic */ class GatedPostAdSummaryView$$Lambda$13 implements Action {
    private final View arg$1;

    private GatedPostAdSummaryView$$Lambda$13(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdSummaryView$$Lambda$13(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showCategory((String) ((Pair) obj).getLeft(), ((Boolean) ((Pair) obj).getRight()).booleanValue());
    }
}
