package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.mvp.Gate.Action;
import java.lang.invoke.LambdaForm.Hidden;

final /* synthetic */ class GatedManualCategorySelectionFragmentView$$Lambda$4 implements Action {
    private final ManualCategorySelectionFragmentPresenter$View arg$1;

    private GatedManualCategorySelectionFragmentView$$Lambda$4(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        this.arg$1 = manualCategorySelectionFragmentPresenter$View;
    }

    public static Action lambdaFactory$(ManualCategorySelectionFragmentPresenter$View manualCategorySelectionFragmentPresenter$View) {
        return new GatedManualCategorySelectionFragmentView$$Lambda$4(manualCategorySelectionFragmentPresenter$View);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.onShowWaiting(((Boolean) obj).booleanValue());
    }
}
