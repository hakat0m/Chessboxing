package com.gumtree.android.category.di;

import com.gumtree.android.category.ManualCategorySelectionActivity;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.service.CategoryRepositoryService;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {ManualCategorySelectionModule.class})
@ManualCategorySelectionScope
public interface ManualCategorySelectionComponent extends BaseComponent {
    public static final String KEY = ManualCategorySelectionComponent.class.getSimpleName();

    CategoryRepositoryService categoryRepositoryService();

    void inject(ManualCategorySelectionActivity manualCategorySelectionActivity);

    ManualCategorySelectionPresenter manualCategorySelectionPresenter();
}
