package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$55 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$55 instance = new DefaultPostAdSummaryPresenter$$Lambda$55();

    private DefaultPostAdSummaryPresenter$$Lambda$55() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return DefaultPostAdSummaryPresenter.lambda$connectMetadataAttributesUpdated$61((CustomDetailsData) obj, (DraftAd) obj2);
    }
}
