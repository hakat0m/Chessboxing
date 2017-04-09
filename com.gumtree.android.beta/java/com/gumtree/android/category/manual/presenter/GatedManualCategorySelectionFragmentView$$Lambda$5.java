package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;
import java.util.List;

final /* synthetic */ class GatedManualCategorySelectionFragmentView$$Lambda$5 implements Action {
    private final ManualCategorySelectionFragmentPresenter$View arg$1;

    private GatedManualCategorySelectionFragmentView$$Lambda$5(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        this.arg$1 = manualCategorySelectionFragmentPresenter$View;
    }

    public static Action lambdaFactory$(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        return new GatedManualCategorySelectionFragmentView$$Lambda$5(manualCategorySelectionFragmentPresenter$View);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onCategoriesUpdated((List) obj);
    }
}
