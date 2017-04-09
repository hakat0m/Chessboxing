package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.DraftLocation;
import com.gumtree.android.postad.presenters.DefaultPostAdSummaryPresenter.PromotionFeaturesTrigger;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import rx.functions.Func5;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$41 implements Func5 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$41 instance = new DefaultPostAdSummaryPresenter$$Lambda$41();

    private DefaultPostAdSummaryPresenter$$Lambda$41() {
    }

    public static Func5 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        return new PromotionFeaturesTrigger((DraftCategory) obj3, (DraftLocation) obj4, (String) obj5, (List) obj2, ((DraftAd) obj).getId());
    }
}
