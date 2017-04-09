package com.gumtree.android.ad.search.keyword.di;

import com.gumtree.android.ad.search.presenters.keyword.DefaultSearchKeywordPresenter;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordGatedView;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter;
import com.gumtree.android.ad.search.services.suggestions.ApiSearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.BackgroundSearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.CachedSearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.SearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.TrackingSearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.converter.SuggestionItemSuggestionModelConverter;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

@Module
public class SearchKeywordModule {
    private static final int SEARCH_KEYWORD_CACHE_CAPACITY = 8;

    @Provides
    @SearchKeywordScope
    SearchKeywordPresenter provideSearchKeywordScreenPresenter(SearchKeywordGatedView searchKeywordGatedView, TrackingSearchKeywordSuggestionsService trackingSearchKeywordSuggestionsService, SearchKeywordSuggestionsService searchKeywordSuggestionsService) {
        return new DefaultSearchKeywordPresenter(searchKeywordGatedView, searchKeywordSuggestionsService, trackingSearchKeywordSuggestionsService, Schedulers.computation(), AndroidSchedulers.mainThread());
    }

    @Provides
    @SearchKeywordScope
    SearchKeywordSuggestionsService provideKeywordSuggestionsService(SuggestionsApi suggestionsApi, @Named("background") Scheduler scheduler) {
        return new BackgroundSearchKeywordSuggestionsService(new CachedSearchKeywordSuggestionsService(new ApiSearchKeywordSuggestionsService(suggestionsApi, new SuggestionItemSuggestionModelConverter()), SEARCH_KEYWORD_CACHE_CAPACITY), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @SearchKeywordScope
    TrackingSearchKeywordSuggestionsService provideTrackingSearchKeywordSuggestionsService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }
}
