package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter.View;
import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import org.apache.commons.lang3.tuple.Pair;

final /* synthetic */ class GatedManualCategorySelectionView$$Lambda$2 implements Action {
    private final View arg$1;

    private GatedManualCategorySelectionView$$Lambda$2(View view) {
        this.arg$1 = view;
    }

    public static Action lambdaFactory$(View view) {
        return new GatedManualCategorySelectionView$$Lambda$2(view);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onShowNextLevel(((Integer) ((Pair) obj).getLeft()).intValue(), (String) ((Pair) obj).getRight());
    }
}
