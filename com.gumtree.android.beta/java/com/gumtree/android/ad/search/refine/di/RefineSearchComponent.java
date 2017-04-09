package com.gumtree.android.ad.search.refine.di;

import com.gumtree.android.ad.search.refine.RefineSearchActivity;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {RefineSearchModule.class})
@RefineSearchScope
public interface RefineSearchComponent extends BaseComponent {
    public static final String KEY = RefineSearchComponent.class.getSimpleName();

    void inject(RefineSearchActivity refineSearchActivity);
}
