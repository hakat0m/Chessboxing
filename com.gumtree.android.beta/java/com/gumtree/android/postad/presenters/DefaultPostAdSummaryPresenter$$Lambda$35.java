package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftLocation;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$35 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$35 instance = new DefaultPostAdSummaryPresenter$$Lambda$35();

    private DefaultPostAdSummaryPresenter$$Lambda$35() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$createPromotionFeaturesTrigger$36((DraftLocation) obj);
    }
}
