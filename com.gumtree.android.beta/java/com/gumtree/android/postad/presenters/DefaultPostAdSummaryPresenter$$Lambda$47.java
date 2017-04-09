package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Triple;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$47 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$47 instance = new DefaultPostAdSummaryPresenter$$Lambda$47();

    private DefaultPostAdSummaryPresenter$$Lambda$47() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return DefaultPostAdSummaryPresenter.lambda$connectCategoryChanged$53((Triple) obj, (DraftAd) obj2);
    }
}
