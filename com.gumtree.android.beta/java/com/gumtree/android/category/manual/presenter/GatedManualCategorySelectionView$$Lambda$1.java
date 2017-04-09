package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter.View;
import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedManualCategorySelectionView$$Lambda$1 implements Action1 {
    private final GatedManualCategorySelectionView arg$1;

    private GatedManualCategorySelectionView$$Lambda$1(GatedManualCategorySelectionView gatedManualCategorySelectionView) {
        this.arg$1 = gatedManualCategorySelectionView;
    }

    public static Action1 lambdaFactory$(GatedManualCategorySelectionView gatedManualCategorySelectionView) {
        return new GatedManualCategorySelectionView$$Lambda$1(gatedManualCategorySelectionView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((View) obj);
    }
}
