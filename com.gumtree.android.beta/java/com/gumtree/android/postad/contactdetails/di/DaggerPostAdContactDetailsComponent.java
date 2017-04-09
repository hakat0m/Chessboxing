package com.gumtree.android.postad.contactdetails.di;

import android.content.Context;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.postad.contactdetails.GatedPostAdContactDetailsView_Factory;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsActivity;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsActivity_MembersInjector;
import com.gumtree.android.postad.contactdetails.PostAdContactDetailsPresenter;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidationMessages;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidationMessages_Factory;
import com.gumtree.android.postad.contactdetails.validation.PostAdContactDetailsValidationRules;
import com.gumtree.android.userprofile.UserProfileStatusService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;

public final class DaggerPostAdContactDetailsComponent implements PostAdContactDetailsComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerPostAdContactDetailsComponent.class.desiredAssertionStatus());
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<Network> networkProvider;
    private MembersInjector<PostAdContactDetailsActivity> postAdContactDetailsActivityMembersInjector;
    private Provider<PostAdContactDetailsValidationMessages> postAdContactDetailsValidationMessagesProvider;
    private Provider<PostAdContactDetailsPresenter> providePostAdContactDetailsPresenterProvider;
    private Provider<PostAdContactDetailsValidationRules> providePostAdValidationRulesProvider;
    private Provider<UserProfileStatusService> userProfileStatusServiceProvider;
    private Provider<UserService> userServiceProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private PostAdContactDetailsModule postAdContactDetailsModule;

        private Builder() {
        }

        public PostAdContactDetailsComponent build() {
            if (this.postAdContactDetailsModule == null) {
                throw new IllegalStateException("postAdContactDetailsModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerPostAdContactDetailsComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder postAdContactDetailsModule(PostAdContactDetailsModule postAdContactDetailsModule) {
            if (postAdContactDetailsModule == null) {
                throw new NullPointerException("postAdContactDetailsModule");
            }
            this.postAdContactDetailsModule = postAdContactDetailsModule;
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

    private DaggerPostAdContactDetailsComponent(Builder builder) {
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
        this.contextProvider = new 4(this, builder);
        this.postAdContactDetailsValidationMessagesProvider = PostAdContactDetailsValidationMessages_Factory.create(this.contextProvider);
        this.providePostAdValidationRulesProvider = ScopedProvider.create(PostAdContactDetailsModule_ProvidePostAdValidationRulesFactory.create(builder.postAdContactDetailsModule, this.postAdContactDetailsValidationMessagesProvider));
        this.userServiceProvider = new 5(this, builder);
        this.userProfileStatusServiceProvider = new 6(this, builder);
        this.providePostAdContactDetailsPresenterProvider = ScopedProvider.create(PostAdContactDetailsModule_ProvidePostAdContactDetailsPresenterFactory.create(builder.postAdContactDetailsModule, GatedPostAdContactDetailsView_Factory.create(), this.providePostAdValidationRulesProvider, this.userServiceProvider, this.userProfileStatusServiceProvider));
        this.postAdContactDetailsActivityMembersInjector = PostAdContactDetailsActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdContactDetailsPresenterProvider);
    }

    public void inject(PostAdContactDetailsActivity postAdContactDetailsActivity) {
        this.postAdContactDetailsActivityMembersInjector.injectMembers(postAdContactDetailsActivity);
    }
}
