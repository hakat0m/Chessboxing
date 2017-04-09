package com.gumtree.android.category.suggest.presenter;

import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedPostAdCategoryView$$Lambda$3 implements Action {
    private final View arg$1;

    private GatedPostAdCategoryView$$Lambda$3(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedPostAdCategoryView$$Lambda$3(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.completeWithCategory((DraftCategory) obj);
    }
}
