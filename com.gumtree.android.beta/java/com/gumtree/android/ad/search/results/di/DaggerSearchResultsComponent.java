package com.gumtree.android.ad.search.results.di;

import com.gumtree.android.ad.search.presenters.results.GatedSearchResultsView_Factory;
import com.gumtree.android.ad.search.presenters.results.SearchResultsPresenter;
import com.gumtree.android.ad.search.results.SearchResultsActivity;
import com.gumtree.android.ad.search.results.SearchResultsActivity_MembersInjector;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.activities.NavigationActivity;
import com.gumtree.android.common.activities.NavigationActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.notifications.IBadgeCounterManager;
import com.gumtree.android.services.NetworkStateService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;

public final class DaggerSearchResultsComponent implements SearchResultsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerSearchResultsComponent.class.desiredAssertionStatus());
    private Provider<IBadgeCounterManager> badgeCounterManagerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private MembersInjector<NavigationActivity> navigationActivityMembersInjector;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<SearchResultsPresenter> provideSearchScreenPresenterProvider;
    private MembersInjector<SearchResultsActivity> searchResultsActivityMembersInjector;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private SearchResultsModule searchResultsModule;

        private Builder() {
        }

        public SearchResultsComponent build() {
            if (this.searchResultsModule == null) {
                this.searchResultsModule = new SearchResultsModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerSearchResultsComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder searchResultsModule(SearchResultsModule searchResultsModule) {
            if (searchResultsModule == null) {
                throw new NullPointerException("searchResultsModule");
            }
            this.searchResultsModule = searchResultsModule;
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

    private DaggerSearchResultsComponent(Builder builder) {
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
        this.badgeCounterManagerProvider = new 4(this, builder);
        this.navigationActivityMembersInjector = NavigationActivity_MembersInjector.create(this.baseActivityMembersInjector, this.eventBusProvider, this.badgeCounterManagerProvider);
        this.networkStateServiceProvider = new 5(this, builder);
        this.provideSearchScreenPresenterProvider = ScopedProvider.create(SearchResultsModule_ProvideSearchScreenPresenterFactory.create(builder.searchResultsModule, GatedSearchResultsView_Factory.create(), this.networkStateServiceProvider));
        this.searchResultsActivityMembersInjector = SearchResultsActivity_MembersInjector.create(this.navigationActivityMembersInjector, this.provideSearchScreenPresenterProvider);
    }

    public void inject(SearchResultsActivity searchResultsActivity) {
        this.searchResultsActivityMembersInjector.injectMembers(searchResultsActivity);
    }
}
