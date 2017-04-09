package com.gumtree.android.ad.search.keyword.di;

import com.gumtree.android.ad.search.keyword.SearchKeywordActivity;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.dagger.components.BaseComponent;
import dagger.Component;

@Component(dependencies = {ApplicationComponent.class}, modules = {SearchKeywordModule.class})
@SearchKeywordScope
public interface SearchKeywordComponent extends BaseComponent {
    public static final String KEY = SearchKeywordComponent.class.getSimpleName();

    void inject(SearchKeywordActivity searchKeywordActivity);
}
