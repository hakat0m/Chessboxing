package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$31 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$31 instance = new DefaultPostAdSummaryPresenter$$Lambda$31();

    private DefaultPostAdSummaryPresenter$$Lambda$31() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((DraftAd) obj).getCategory();
    }
}
