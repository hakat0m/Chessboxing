package com.gumtree.android.category.di;

import com.gumtree.android.category.ManualCategorySelectionFragment;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ManualCategorySelectionComponent.class}, modules = {ManualCategorySelectionFragmentModule.class})
@ManualCategoryFragmentScope
public interface ManualCategorySelectionFragmentComponent extends BaseComponent {
    public static final String KEY = ManualCategorySelectionFragmentComponent.class.getSimpleName();

    void inject(ManualCategorySelectionFragment manualCategorySelectionFragment);
}
