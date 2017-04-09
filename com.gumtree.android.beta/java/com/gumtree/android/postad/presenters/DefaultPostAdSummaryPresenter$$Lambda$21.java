package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$21 implements Func1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$21(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Func1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$21(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$connectPriceFrequencyClear$22((DraftCategory) obj);
    }
}
