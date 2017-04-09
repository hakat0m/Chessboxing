package com.gumtree.android.category.suggest.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class DefaultPostAdCategoryPresenter$$Lambda$4 implements Action1 {
    private final DefaultPostAdCategoryPresenter arg$1;

    private DefaultPostAdCategoryPresenter$$Lambda$4(DefaultPostAdCategoryPresenter defaultPostAdCategoryPresenter) {
        this.arg$1 = defaultPostAdCategoryPresenter;
    }

    public static Action1 lambdaFactory$(DefaultPostAdCategoryPresenter defaultPostAdCategoryPresenter) {
        return new DefaultPostAdCategoryPresenter$$Lambda$4(defaultPostAdCategoryPresenter);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$onListSuggestions$3((Throwable) obj);
    }
}
