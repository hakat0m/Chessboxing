package com.gumtree.android.category.suggest.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action0;

final /* synthetic */ class DefaultPostAdCategoryPresenter$$Lambda$1 implements Action0 {
    private final DefaultPostAdCategoryPresenter arg$1;

    private DefaultPostAdCategoryPresenter$$Lambda$1(DefaultPostAdCategoryPresenter defaultPostAdCategoryPresenter) {
        this.arg$1 = defaultPostAdCategoryPresenter;
    }

    public static Action0 lambdaFactory$(DefaultPostAdCategoryPresenter defaultPostAdCategoryPresenter) {
        return new DefaultPostAdCategoryPresenter$$Lambda$1(defaultPostAdCategoryPresenter);
    }

    @Hidden
    public void call() {
        this.arg$1.lambda$onListSuggestions$0();
    }
}
