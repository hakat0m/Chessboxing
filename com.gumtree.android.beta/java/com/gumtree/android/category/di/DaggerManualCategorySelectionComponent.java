package com.gumtree.android.category.di;

import android.content.Context;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.category.ManualCategorySelectionActivity;
import com.gumtree.android.category.ManualCategorySelectionActivity_MembersInjector;
import com.gumtree.android.category.manual.presenter.GatedManualCategorySelectionView_Factory;
import com.gumtree.android.category.manual.presenter.ManualCategorySelectionPresenter;
import com.gumtree.android.category.manual.service.CategoryRepositoryService;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerManualCategorySelectionComponent implements ManualCategorySelectionComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerManualCategorySelectionComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private MembersInjector<ManualCategorySelectionActivity> manualCategorySelectionActivityMembersInjector;
    private Provider<Network> networkProvider;
    private Provider<CategoryRepositoryService> provideCategoryRepositoryServiceProvider;
    private Provider<ManualCategorySelectionPresenter> provideManualCategorySelectionPresenterProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private ManualCategorySelectionModule manualCategorySelectionModule;

        private Builder() {
        }

        public ManualCategorySelectionComponent build() {
            if (this.manualCategorySelectionModule == null) {
                this.manualCategorySelectionModule = new ManualCategorySelectionModule();
            }
            if (this.applicationComponent != null) {
                return new DaggerManualCategorySelectionComponent();
            }
            throw new IllegalStateException("applicationComponent must be set");
        }

        public Builder manualCategorySelectionModule(ManualCategorySelectionModule manualCategorySelectionModule) {
            if (manualCategorySelectionModule == null) {
                throw new NullPointerException("manualCategorySelectionModule");
            }
            this.manualCategorySelectionModule = manualCategorySelectionModule;
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

    private DaggerManualCategorySelectionComponent(Builder builder) {
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
        this.contextProvider = new 1(this, builder);
        this.backgroundSchedulerProvider = new 2(this, builder);
        this.provideCategoryRepositoryServiceProvider = ScopedProvider.create(ManualCategorySelectionModule_ProvideCategoryRepositoryServiceFactory.create(builder.manualCategorySelectionModule, this.contextProvider, this.backgroundSchedulerProvider));
        this.provideManualCategorySelectionPresenterProvider = ScopedProvider.create(ManualCategorySelectionModule_ProvideManualCategorySelectionPresenterFactory.create(builder.manualCategorySelectionModule, GatedManualCategorySelectionView_Factory.create()));
        this.eventBusProvider = new 3(this, builder);
        this.networkProvider = new 4(this, builder);
        this.baseAccountManagerProvider = new 5(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.manualCategorySelectionActivityMembersInjector = ManualCategorySelectionActivity_MembersInjector.create(this.baseActivityMembersInjector, this.provideManualCategorySelectionPresenterProvider);
    }

    public CategoryRepositoryService categoryRepositoryService() {
        return (CategoryRepositoryService) this.provideCategoryRepositoryServiceProvider.get();
    }

    public ManualCategorySelectionPresenter manualCategorySelectionPresenter() {
        return (ManualCategorySelectionPresenter) this.provideManualCategorySelectionPresenterProvider.get();
    }

    public void inject(ManualCategorySelectionActivity manualCategorySelectionActivity) {
        this.manualCategorySelectionActivityMembersInjector.injectMembers(manualCategorySelectionActivity);
    }
}
