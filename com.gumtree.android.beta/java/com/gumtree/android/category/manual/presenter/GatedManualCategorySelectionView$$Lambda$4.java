package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedManualCategorySelectionView$$Lambda$4 implements Action {
    private final View arg$1;

    private GatedManualCategorySelectionView$$Lambda$4(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedManualCategorySelectionView$$Lambda$4(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onUpdateTitle((String) obj);
    }
}
