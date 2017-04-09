package com.gumtree.android.ad.search.results.di;

import com.gumtree.android.ad.search.results.SearchResultsActivity;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@SearchResultsScope
@Component(dependencies = {ApplicationComponent.class}, modules = {SearchResultsModule.class})
public interface SearchResultsComponent extends BaseComponent {
    public static final String KEY = SearchResultsComponent.class.getSimpleName();

    void inject(SearchResultsActivity searchResultsActivity);
}
