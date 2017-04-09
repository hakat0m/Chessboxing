package com.gumtree.android.ad.search.refine.di;

import com.gumtree.andorid.ad.search.services.refine.TrackingRefinePanelService;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchGatedView_Factory;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter;
import com.gumtree.android.ad.search.refine.RefineSearchActivity;
import com.gumtree.android.ad.search.refine.RefineSearchActivity_MembersInjector;
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

public final class DaggerRefineSearchComponent implements RefineSearchComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerRefineSearchComponent.class.desiredAssertionStatus());
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private Provider<RefineSearchPresenter> provideRefineSearchPresenterProvider;
    private Provider<TrackingRefinePanelService> provideTrackingRefinePanelServiceProvider;
    private MembersInjector<RefineSearchActivity> refineSearchActivityMembersInjector;
    private Provider<StaticTrackingService> staticTrackingServiceProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private RefineSearchModule refineSearchModule;

        private Builder() {
        }

        public RefineSearchComponent build() {
            if (this.refineSearchModule == null) {
                this.refineSearchModule = new RefineSearchModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerRefineSearchComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder refineSearchModule(RefineSearchModule refineSearchModule) {
            if (refineSearchModule == null) {
                throw new NullPointerException("refineSearchModule");
            }
            this.refineSearchModule = refineSearchModule;
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

    private DaggerRefineSearchComponent(Builder builder) {
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
        this.provideTrackingRefinePanelServiceProvider = ScopedProvider.create(RefineSearchModule_ProvideTrackingRefinePanelServiceFactory.create(builder.refineSearchModule, this.staticTrackingServiceProvider));
        this.provideRefineSearchPresenterProvider = ScopedProvider.create(RefineSearchModule_ProvideRefineSearchPresenterFactory.create(builder.refineSearchModule, RefineSearchGatedView_Factory.create(), this.provideTrackingRefinePanelServiceProvider));
        this.refineSearchActivityMembersInjector = RefineSearchActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideRefineSearchPresenterProvider);
    }

    public void inject(RefineSearchActivity refineSearchActivity) {
        this.refineSearchActivityMembersInjector.injectMembers(refineSearchActivity);
    }
}
