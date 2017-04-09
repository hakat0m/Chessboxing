package com.gumtree.android.postad.confirmation.di;

import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.postad.confirmation.GatedPostAdConfirmationScreenView_Factory;
import com.gumtree.android.postad.confirmation.GetDraftAdHelper;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenActivity;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenActivity_MembersInjector;
import com.gumtree.android.postad.confirmation.PostAdConfirmationScreenPresenter;
import com.gumtree.android.postad.services.PostAdService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;

public final class DaggerPostAdConfirmationScreenComponent implements PostAdConfirmationScreenComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerPostAdConfirmationScreenComponent.class.desiredAssertionStatus());
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private MembersInjector<PostAdConfirmationScreenActivity> postAdConfirmationScreenActivityMembersInjector;
    private Provider<PostAdService> postAdServiceProvider;
    private Provider<GetDraftAdHelper> provideGetDraftAdHelperProvider;
    private Provider<PostAdConfirmationScreenPresenter> providePostAdConfirmationScreenPresenterProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private PostAdConfirmationScreenModule postAdConfirmationScreenModule;

        private Builder() {
        }

        public PostAdConfirmationScreenComponent build() {
            if (this.postAdConfirmationScreenModule == null) {
                throw new IllegalStateException("postAdConfirmationScreenModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerPostAdConfirmationScreenComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder postAdConfirmationScreenModule(PostAdConfirmationScreenModule postAdConfirmationScreenModule) {
            if (postAdConfirmationScreenModule == null) {
                throw new NullPointerException("postAdConfirmationScreenModule");
            }
            this.postAdConfirmationScreenModule = postAdConfirmationScreenModule;
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

    private DaggerPostAdConfirmationScreenComponent(Builder builder) {
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
        this.postAdServiceProvider = new 1(this, builder);
        this.provideGetDraftAdHelperProvider = ScopedProvider.create(PostAdConfirmationScreenModule_ProvideGetDraftAdHelperFactory.create(builder.postAdConfirmationScreenModule, this.postAdServiceProvider));
        this.providePostAdConfirmationScreenPresenterProvider = ScopedProvider.create(PostAdConfirmationScreenModule_ProvidePostAdConfirmationScreenPresenterFactory.create(builder.postAdConfirmationScreenModule, GatedPostAdConfirmationScreenView_Factory.create(), this.provideGetDraftAdHelperProvider));
        this.eventBusProvider = new 2(this, builder);
        this.networkProvider = new 3(this, builder);
        this.baseAccountManagerProvider = new 4(this, builder);
        this.baseActivityMembersInjector = BaseActivity_MembersInjector.create(MembersInjectors.noOp(), this.eventBusProvider, this.networkProvider, this.baseAccountManagerProvider);
        this.postAdConfirmationScreenActivityMembersInjector = PostAdConfirmationScreenActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdConfirmationScreenPresenterProvider);
    }

    public PostAdConfirmationScreenPresenter providePostAdConfirmationScreenPresenter() {
        return (PostAdConfirmationScreenPresenter) this.providePostAdConfirmationScreenPresenterProvider.get();
    }

    public void inject(PostAdConfirmationScreenActivity postAdConfirmationScreenActivity) {
        this.postAdConfirmationScreenActivityMembersInjector.injectMembers(postAdConfirmationScreenActivity);
    }
}
