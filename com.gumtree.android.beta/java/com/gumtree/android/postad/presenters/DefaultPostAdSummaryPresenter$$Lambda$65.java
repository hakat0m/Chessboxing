package com.gumtree.android.postad.presenters;

import com.gumtree.android.category.model.DraftCategory;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Func1;

final /* synthetic */ class DefaultPostAdSummaryPresenter$$Lambda$65 implements Func1 {
    private static final DefaultPostAdSummaryPresenter$$Lambda$65 instance = new DefaultPostAdSummaryPresenter$$Lambda$65();

    private DefaultPostAdSummaryPresenter$$Lambda$65() {
    }

    public static Func1 lambdaFactory$() {
        return instance;
    }

    @Hidden
    public Object call(Object obj) {
        return DefaultPostAdSummaryPresenter.lambda$connectMetadataService$71((DraftCategory) obj);
    }
}
