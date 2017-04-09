package com.gumtree.android.category.di;

import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {CategoryModule.class})
@CategoryScope
public interface CategoryComponent extends BaseComponent {
    public static final String KEY = CategoryComponent.class.getSimpleName();

    void inject(NewPostAdCategoryActivity newPostAdCategoryActivity);
}
