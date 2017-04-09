package com.gumtree.android.postad.presenters;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$2 implements Action1 {
    private final GatedPostAdSummaryView arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$2(GatedPostAdSummaryView gatedPostAdSummaryView) {
        this.arg$1 = gatedPostAdSummaryView;
    }

    public static Action1 lambdaFactory$(GatedPostAdSummaryView gatedPostAdSummaryView) {
        return new DefaultPostAdSummaryPresenter$$Lambda$2(gatedPostAdSummaryView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.showLoading(((Boolean) obj).booleanValue());
    }
}
