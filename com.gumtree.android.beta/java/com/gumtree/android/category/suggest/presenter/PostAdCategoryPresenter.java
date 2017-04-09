package com.gumtree.android.category.suggest.presenter;

import android.support.annotation.NonNull;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.mvp.Presenter;
import com.gumtree.android.mvp.PresenterView;
import java.util.List;

public interface PostAdCategoryPresenter extends Presenter<View> {

    public interface View extends PresenterView {
        void completeWithCategory(DraftCategory draftCategory);

        void showLoading(boolean z);

        void showManualCategorySelection(boolean z);

        void showSuggestedCategoriesTitle(boolean z);

        void showSuggestions(@NonNull List<DraftCategory> list);
    }

    void onListSuggestions(String str, boolean z);

    void onSuggestionSelected(int i);
}
