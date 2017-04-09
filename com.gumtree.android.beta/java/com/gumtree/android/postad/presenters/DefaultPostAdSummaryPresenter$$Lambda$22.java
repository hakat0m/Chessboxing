package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$22 implements Action1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$22 instance = new DefaultPostAdSummaryPresenter$$Lambda$22();

    private DefaultPostAdSummaryPresenter$$Lambda$22() {
    }

    public static Action1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public void call(Object obj) {
        ((DraftAd) obj).setSelectedPriceFrequency(null);
    }
}
