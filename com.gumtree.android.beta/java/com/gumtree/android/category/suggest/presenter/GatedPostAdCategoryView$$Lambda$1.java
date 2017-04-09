package com.gumtree.android.category.suggest.presenter;

import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedPostAdCategoryView$$Lambda$1 implements Action1 {
    private final GatedPostAdCategoryView arg$1;

    private GatedPostAdCategoryView$$Lambda$1(GatedPostAdCategoryView gatedPostAdCategoryView) {
        this.arg$1 = gatedPostAdCategoryView;
    }

    public static Action1 lambdaFactory$(GatedPostAdCategoryView gatedPostAdCategoryView) {
        return new GatedPostAdCategoryView$$Lambda$1(gatedPostAdCategoryView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
