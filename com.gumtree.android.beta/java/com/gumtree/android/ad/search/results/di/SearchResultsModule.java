package com.gumtree.android.ad.search.results.di;

import com.gumtree.android.ad.search.presenters.results.DefaultSearchResultsPresenter;
import com.gumtree.android.ad.search.presenters.results.GatedSearchResultsView;
import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter;
import com.gumtree.android.services.NetworkStateService;
import dagger.Module;
import dagger.Provides;

@Module
public class SearchResultsModule {
    @Provides
    @SearchResultsScope
    SearchResultsPresenter provideSearchScreenPresenter(GatedSearchResultsView gatedSearchResultsView, NetworkStateService networkStateService) {
        return new DefaultSearchResultsPresenter(gatedSearchResultsView, networkStateService);
    }
}
