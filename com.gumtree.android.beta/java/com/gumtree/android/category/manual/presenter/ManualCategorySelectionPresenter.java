package com.gumtree.android.category.manual.presenter;

import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;

public interface ManualCategorySelectionPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void onCategorySelected(DraftCategory draftCategory);

        void onShowNextLevel(int i, String str);

        void onUpdateTitle(String str);
    }

    void categoryLeafSelected(DraftCategory draftCategory);

    void categoryNodeSelected(DraftCategory draftCategory);

    void updateTitle(String str);
}
