package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAd;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$39 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$39 instance = new DefaultPostAdSummaryPresenter$$Lambda$39();

    private DefaultPostAdSummaryPresenter$$Lambda$39() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((DraftAd) obj).getAttributesValues();
    }
}
