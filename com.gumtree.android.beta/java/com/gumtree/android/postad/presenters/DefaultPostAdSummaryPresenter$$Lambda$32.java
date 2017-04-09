package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$32 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$32 instance = new DefaultPostAdSummaryPresenter$$Lambda$32();

    private DefaultPostAdSummaryPresenter$$Lambda$32() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$createPromotionFeaturesTrigger$33((DraftCategory) obj);
    }
}
