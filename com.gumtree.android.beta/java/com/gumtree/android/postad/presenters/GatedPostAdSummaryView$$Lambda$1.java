package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.presenters.PostAdSummaryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPostAdSummaryView$$Lambda$1 implements Action1 {
    private final GatedPostAdSummaryView arg$1;

    private GatedPostAdSummaryView$$Lambda$1(GatedPostAdSummaryView gatedPostAdSummaryView) {
        this.arg$1 = gatedPostAdSummaryView;
    }

    public static Action1 lambdaFactory$(GatedPostAdSummaryView gatedPostAdSummaryView) {
        return new GatedPostAdSummaryView$$Lambda$1(gatedPostAdSummaryView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
