package com.gumtree.android.category.manual.presenter;

import java.lang.invoke.LambdaForm.Hidden;
import rx.functions.Action1;

final /* synthetic */ class GatedManualCategorySelectionFragmentView$$Lambda$1 implements Action1 {
    private final GatedManualCategorySelectionFragmentView arg$1;

    private GatedManualCategorySelectionFragmentView$$Lambda$1(GatedManualCategorySelectionFragmentView gatedManualCategorySelectionFragmentView) {
        this.arg$1 = gatedManualCategorySelectionFragmentView;
    }

    public static Action1 lambdaFactory$(GatedManualCategorySelectionFragmentView gatedManualCategorySelectionFragmentView) {
        return new GatedManualCategorySelectionFragmentView$$Lambda$1(gatedManualCategorySelectionFragmentView);
    }

    @Hidden
    public void call(Object obj) {
        this.arg$1.lambda$new$0((ManualCategorySelectionFragmentPresenter$View) obj);
    }
}
