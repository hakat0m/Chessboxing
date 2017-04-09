package com.gumtree.android.postad.presenters;

import com.gumtree.android.mvp.Gate.Action;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import org.apache.commons.lang3.tuple.Triple;

final /* synthetic */ class GatedPostAdSummaryView$$Lambda$36 implements Action {
    private final View arg$1;

    private GatedPostAdSummaryView$$Lambda$36(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdSummaryView$$Lambda$36(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.addImage((List) ((Triple) obj).getLeft(), ((Integer) ((Triple) obj).getMiddle()).intValue(), (String) ((Triple) obj).getRight());
    }
}
