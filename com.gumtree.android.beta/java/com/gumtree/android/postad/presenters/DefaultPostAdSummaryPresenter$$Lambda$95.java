package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.PostAdImage;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$95 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$95 instance = new DefaultPostAdSummaryPresenter$$Lambda$95();

    private DefaultPostAdSummaryPresenter$$Lambda$95() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$null$49((PostAdImage) obj);
    }
}
