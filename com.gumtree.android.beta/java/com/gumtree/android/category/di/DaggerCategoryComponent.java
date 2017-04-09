package com.gumtree.android.category.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.category.NewPostAdCategoryActivity;
import com.gumtree.android.category.NewPostAdCategoryActivity_MembersInjector;
import com.gumtree.android.category.suggest.presenter.GatedPostAdCategoryView_Factory;
import com.gumtree.android.category.suggest.presenter.PostAdCategoryPresenter;
import com.gumtree.android.category.suggest.service.CategoryService;
import com.gumtree.android.category.suggest.service.converter.CategoryTreeConverter;
import com.gumtree.android.category.suggest.service.converter.CategoryTrunkConverter;
import com.gumtree.android.category.suggest.service.converter.CategoryTrunkConverter_Factory;
import com.gumtree.android.category.suggest.service.converter.DefaultCategoryTreeConverter_Factory;
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

public final class DaggerCategoryComponent implements CategoryComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerCategoryComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<CategoryTrunkConverter> categoryTrunkConverterProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private MembersInjector<NewPostAdCategoryActivity> newPostAdCategoryActivityMembersInjector;
    private Provider<CategoryService> provideCategoryServiceProvider;
    private Provider<CategoryTreeConverter> provideCategoryTrunkConverterProvider;
    private Provider<PostAdCategoryPresenter> providePostAdCategoryPresenterProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private CategoryModule categoryModule;

        private Builder() {
        }

        public CategoryComponent build() {
            if (this.categoryModule == null) {
                throw new IllegalStateException("categoryModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerCategoryComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder categoryModule(CategoryModule categoryModule) {
            if (categoryModule == null) {
                throw new NullPointerException("categoryModule");
            }
            this.categoryModule = categoryModule;
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

    private DaggerCategoryComponent(Builder builder) {
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
        this.xmlClientProvider = new 4(this, builder);
        this.backgroundSchedulerProvider = new 5(this, builder);
        this.provideCategoryServiceProvider = ScopedProvider.create(CategoryModule_ProvideCategoryServiceFactory.create(builder.categoryModule, this.xmlClientProvider, this.backgroundSchedulerProvider, DefaultCategoryTreeConverter_Factory.create()));
        this.providePostAdCategoryPresenterProvider = ScopedProvider.create(CategoryModule_ProvidePostAdCategoryPresenterFactory.create(builder.categoryModule, this.provideCategoryServiceProvider, GatedPostAdCategoryView_Factory.create()));
        this.categoryTrunkConverterProvider = CategoryTrunkConverter_Factory.create(DefaultCategoryTreeConverter_Factory.create());
        this.provideCategoryTrunkConverterProvider = ScopedProvider.create(CategoryModule_ProvideCategoryTrunkConverterFactory.create(builder.categoryModule, this.categoryTrunkConverterProvider));
        this.newPostAdCategoryActivityMembersInjector = NewPostAdCategoryActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdCategoryPresenterProvider, this.provideCategoryTrunkConverterProvider);
    }

    public void inject(NewPostAdCategoryActivity newPostAdCategoryActivity) {
        this.newPostAdCategoryActivityMembersInjector.injectMembers(newPostAdCategoryActivity);
    }
}
