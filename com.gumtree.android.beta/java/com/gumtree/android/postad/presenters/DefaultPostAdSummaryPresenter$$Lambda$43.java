package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.contactdetails.models.ContactDetailsData;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$43 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$43 instance = new DefaultPostAdSummaryPresenter$$Lambda$43();

    private DefaultPostAdSummaryPresenter$$Lambda$43() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return Pair.of((ContactDetailsData) obj, (DraftAd) obj2);
    }
}
