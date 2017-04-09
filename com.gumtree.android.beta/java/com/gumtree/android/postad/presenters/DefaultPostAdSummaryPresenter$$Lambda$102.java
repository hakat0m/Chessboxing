package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.DraftAdAttribute;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$102 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$102 instance = new DefaultPostAdSummaryPresenter$$Lambda$102();

    private DefaultPostAdSummaryPresenter$$Lambda$102() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return ((DraftAdAttribute) obj).getValue();
    }
}
