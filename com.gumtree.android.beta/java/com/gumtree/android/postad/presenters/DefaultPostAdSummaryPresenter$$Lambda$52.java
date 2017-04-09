package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;
import org.apache.commons.lang3.tuple.Pair;
import rx.functions.Func2;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$52 implements Func2 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$52 instance = new DefaultPostAdSummaryPresenter$$Lambda$52();

    private DefaultPostAdSummaryPresenter$$Lambda$52() {
    }

    public static Func2 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj, Object obj2) {
        return Pair.of((List) obj, (DraftAd) obj2);
    }
}
