package com.gumtree.android.category.di;

import com.gumtree.android.category.manual.presenter.DefaultManualCategorySelectionFragmentPresenter;
import com.gumtree.android.category.manual.presenter.GatedManualCategorySelectionFragmentView;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionFragmentPresenter;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.service.CategoryRepositoryService;
import dagger.Module;
import dagger.Provides;

@Module
public class ManualCategorySelectionFragmentModule {
    @Provides
    @ManualCategoryFragmentScope
    public ManualCategorySelectionFragmentPresenter provideManualCategorySelectionPresenter(ManualCategorySelectionPresenter manualCategorySelectionPresenter, CategoryRepositoryService categoryRepositoryService, GatedManualCategorySelectionFragmentView gatedManualCategorySelectionFragmentView) {
        return new DefaultManualCategorySelectionFragmentPresenter(manualCategorySelectionPresenter, categoryRepositoryService, gatedManualCategorySelectionFragmentView);
    }
}
