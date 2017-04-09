package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.mvp.PresenterView;
import java.util.List;

public interface ManualCategorySelectionFragmentPresenter$View extends PresenterView {
    void onCategoriesUpdated(List<DraftCategory> list);

    void onShowWaiting(boolean z);
}
