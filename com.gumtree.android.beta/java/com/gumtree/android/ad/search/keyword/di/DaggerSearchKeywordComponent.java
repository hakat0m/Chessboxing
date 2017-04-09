package com.gumtree.android.ad.search.keyword.di;

import com.gumtree.android.ad.search.keyword.SearchKeywordActivity;
import com.gumtree.android.ad.search.keyword.SearchKeywordActivity_MembersInjector;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordGatedView_Factory;
import com.gumtree.android.ad.search.presenters.keyword.SearchKeywordPresenter;
import com.gumtree.android.ad.search.services.suggestions.SearchKeywordSuggestionsService;
import com.gumtree.android.ad.search.services.suggestions.TrackingSearchKeywordSuggestionsService;
import com.gumtree.android.api.callisto.suggest.SuggestionsApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.services.StaticTrackingService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerSearchKeywordComponent implements SearchKeywordComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSearchKeywordComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private Provider<SearchKeywordSuggestionsService> provideKeywordSuggestionsServiceProvider;
    private Provider<SearchKeywordPresenter> provideSearchKeywordScreenPresenterProvider;
    private Provider<TrackingSearchKeywordSuggestionsService> provideTrackingSearchKeywordSuggestionsServiceProvider;
    private MembersInjector<SearchKeywordActivity> searchKeywordActivityMembersInjector;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;
    private Provider<SuggestionsApi> suggestionsApiProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private SearchKeywordModule searchKeywordModule;

        private Builder() {
        }

        public SearchKeywordComponent build() {
            if (this.searchKeywordModule == null) {
                this.searchKeywordModule = new SearchKeywordModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerSearchKeywordComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder searchKeywordModule(SearchKeywordModule searchKeywordModule) {
            if (searchKeywordModule == null) {
                throw new NullPointerException("searchKeywordModule");
            }
            this.searchKeywordModule = searchKeywordModule;
            return this;
        }

        public Builder applicationComponent(ApplicationComponent applicationComponent) {
            if (applicationComponent == null) {
                throw new NullPointerException("applicationComponent");
            }
            this.applicationComponent = applicationComponent;
            return this;
        }
    }

    private DaggerSearchKeywordComponent(Builder builder) {
        if ($assertionsDisabled || builder != null) {
            initialize(builder);
            return;
        }
        throw new AssertionError();
    }

    public static Builder builder() {
        return new Builder();
    }

    private void initialize(Builder builder) {
        this.eventBusProvider = new 1(this, builder);
        this.networkProvider = new 2(this, builder);
        this.baseAccountManagerProvider = new 3(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.staticTrackingServiceProvider = new 4(this, builder);
        this.provideTrackingSearchKeywordSuggestionsServiceProvider = ScopedProvider.create(SearchKeywordModule_ProvideTrackingSearchKeywordSuggestionsServiceFactory.create(builder.searchKeywordModule, this.staticTrackingServiceProvider));
        this.suggestionsApiProvider = new 5(this, builder);
        this.backgroundSchedulerProvider = new 6(this, builder);
        this.provideKeywordSuggestionsServiceProvider = ScopedProvider.create(SearchKeywordModule_ProvideKeywordSuggestionsServiceFactory.create(builder.searchKeywordModule, this.suggestionsApiProvider, this.backgroundSchedulerProvider));
        this.provideSearchKeywordScreenPresenterProvider = ScopedProvider.create(SearchKeywordModule_ProvideSearchKeywordScreenPresenterFactory.create(builder.searchKeywordModule, SearchKeywordGatedView_Factory.create(), this.provideTrackingSearchKeywordSuggestionsServiceProvider, this.provideKeywordSuggestionsServiceProvider));
        this.searchKeywordActivityMembersInjector = SearchKeywordActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideSearchKeywordScreenPresenterProvider);
    }

    public void inject(SearchKeywordActivity searchKeywordActivity) {
        this.searchKeywordActivityMembersInjector.injectMembers(searchKeywordActivity);
    }
}
