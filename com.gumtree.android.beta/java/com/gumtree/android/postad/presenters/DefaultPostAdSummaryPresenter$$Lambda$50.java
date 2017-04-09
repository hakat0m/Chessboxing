package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$50 implements Action1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$50(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$50(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$connectCategoryChanged$56((DraftCategory) obj);
    }
}
