package com.gumtree.android.postad.presenters;

import com.gumtree.android.postad.PostAdImage;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$96 implements Func1 {
    private final DefaultPostAdSummaryPresenter arg$1;

    private DefaultPostAdSummaryPresenter$$Lambda$96(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        this.arg$1 = defaultPostAdSummaryPresenter;
    }

    public static Func1 lambdaFactory$(DefaultPostAdSummaryPresenter defaultPostAdSummaryPresenter) {
        return new DefaultPostAdSummaryPresenter$$Lambda$96(defaultPostAdSummaryPresenter);
    }

    @Hidden
    public Object call(Object obj) {
        return this.arg$1.lambda$null$50((PostAdImage) obj);
    }
}
