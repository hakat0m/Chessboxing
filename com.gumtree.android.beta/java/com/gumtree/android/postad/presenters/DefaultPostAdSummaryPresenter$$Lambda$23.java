package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.presenters.DefaultPostAdSummaryPresenter.PromotionFeaturesTrigger;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$23 implements Func1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$23(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Func1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$23(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$connectSelectedFeaturesAndPriceInfo$24((PromotionFeaturesTrigger) obj);
    }
}
