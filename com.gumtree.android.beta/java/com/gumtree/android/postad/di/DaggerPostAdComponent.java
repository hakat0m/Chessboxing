package com.gumtree.android.postad.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.pictures.PicturesApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.common.activities.BaseActivity;
import com.gumtree.android.common.activities.BaseActivity_MembersInjector;
import com.gumtree.android.common.connectivity.Network;
import com.gumtree.android.dagger.components.ApplicationComponent;
import com.gumtree.android.events.EventBus;
import com.gumtree.android.metadata.service.MetadataService;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.PostAdActivity;
import com.gumtree.android.postad.PostAdActivity_MembersInjector;
import com.gumtree.android.postad.presenters.GatedPostAdSummaryView_Factory;
import com.gumtree.android.postad.presenters.MetadataBee;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter;
import com.gumtree.android.postad.presenters.PriceInfoBee;
import com.gumtree.android.postad.services.CapiImageUploadService;
import com.gumtree.android.postad.services.CapiImageUploadService_Factory;
import com.gumtree.android.postad.services.ContactDetailsDataDraftAdModelConverter;
import com.gumtree.android.postad.services.ImageDownloadService;
import com.gumtree.android.postad.services.ImageUploadService;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.validation.PostAdValidationMessages;
import com.gumtree.android.postad.validation.PostAdValidationMessages_Factory;
import com.gumtree.android.postad.validation.PostAdValidationRules;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.userprofile.services.UserService;
import dagger.MembersInjector;
import dagger.internal.MembersInjectors;
import dagger.internal.ScopedProvider;
import javax.inject.Provider;
import rx.Scheduler;

public final class DaggerPostAdComponent implements PostAdComponent {
    static final /* synthetic */ boolean $assertionsDisabled = (!DaggerPostAdComponent.class.desiredAssertionStatus());
    private Provider<Scheduler> backgroundSchedulerProvider;
    private Provider<BaseAccountManager> baseAccountManagerProvider;
    private MembersInjector<BaseActivity> baseActivityMembersInjector;
    private Provider<CapiImageUploadService> capiImageUploadServiceProvider;
    private Provider<Context> contextProvider;
    private Provider<EventBus> eventBusProvider;
    private Provider<LocalisedTextProvider> localisedTextProvider;
    private Provider<Network> networkProvider;
    private Provider<NetworkStateService> networkStateServiceProvider;
    private Provider<ICapiClient> picturesUploadClientProvider;
    private MembersInjector<PostAdActivity> postAdActivityMembersInjector;
    private Provider<Repository<DraftAd>> postAdRepositoryProvider;
    private Provider<PostAdService> postAdServiceProvider;
    private Provider<PostAdValidationMessages> postAdValidationMessagesProvider;
    private Provider<PriceInfoService> priceInfoServiceProvider;
    private Provider<String> provideAdIdProvider;
    private Provider<ContactDetailsDataDraftAdModelConverter> provideContactDetailsDataDraftAdModelConverterProvider;
    private Provider<ImageDownloadService> provideImageDownloadServiceProvider;
    private Provider<ImageUploadService> provideImageUploadServiceProvider;
    private Provider<MetadataBee> provideMetadataBeeProvider;
    private Provider<MetadataService> provideMetadataServiceProvider;
    private Provider<PicturesApi> providePicturesApiProvider;
    private Provider<PostAdSummaryPresenter> providePostAdSummaryPresenterProvider;
    private Provider<PostAdValidationRules> providePostAdValidationRulesProvider;
    private Provider<PriceInfoBee> providePriceInfoBeeProvider;
    private Provider<TrackingPostAdService> trackingPostAdServiceProvider;
    private Provider<UserService> userServiceProvider;
    private Provider<ICapiClient> xmlClientProvider;

    public final class Builder {
        private ApplicationComponent applicationComponent;
        private PostAdModule postAdModule;

        private Builder() {
        }

        public PostAdComponent build() {
            if (this.postAdModule == null) {
                throw new IllegalStateException("postAdModule must be set");
            } else if (this.applicationComponent != null) {
                return new DaggerPostAdComponent();
            } else {
                throw new IllegalStateException("applicationComponent must be set");
            }
        }

        public Builder postAdModule(PostAdModule postAdModule) {
            if (postAdModule == null) {
                throw new NullPointerException("postAdModule");
            }
            this.postAdModule = postAdModule;
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

    private DaggerPostAdComponent(Builder builder) {
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
        this.postAdServiceProvider = new 4(this, builder);
        this.postAdRepositoryProvider = new 5(this, builder);
        this.picturesUploadClientProvider = new 6(this, builder);
        this.providePicturesApiProvider = ScopedProvider.create(PostAdModule_ProvidePicturesApiFactory.create(builder.postAdModule, this.picturesUploadClientProvider));
        this.capiImageUploadServiceProvider = CapiImageUploadService_Factory.create(this.providePicturesApiProvider);
        this.backgroundSchedulerProvider = new 7(this, builder);
        this.provideImageUploadServiceProvider = ScopedProvider.create(PostAdModule_ProvideImageUploadServiceFactory.create(builder.postAdModule, this.capiImageUploadServiceProvider, this.backgroundSchedulerProvider));
        this.contextProvider = new 8(this, builder);
        this.provideImageDownloadServiceProvider = ScopedProvider.create(PostAdModule_ProvideImageDownloadServiceFactory.create(builder.postAdModule, this.contextProvider, this.backgroundSchedulerProvider));
        this.postAdValidationMessagesProvider = PostAdValidationMessages_Factory.create(this.contextProvider);
        this.providePostAdValidationRulesProvider = ScopedProvider.create(PostAdModule_ProvidePostAdValidationRulesFactory.create(builder.postAdModule, this.postAdValidationMessagesProvider));
        this.localisedTextProvider = new 9(this, builder);
        this.userServiceProvider = new 10(this, builder);
        this.priceInfoServiceProvider = new 11(this, builder);
        this.providePriceInfoBeeProvider = ScopedProvider.create(PostAdModule_ProvidePriceInfoBeeFactory.create(builder.postAdModule, this.priceInfoServiceProvider));
        this.provideContactDetailsDataDraftAdModelConverterProvider = ScopedProvider.create(PostAdModule_ProvideContactDetailsDataDraftAdModelConverterFactory.create(builder.postAdModule));
        this.networkStateServiceProvider = new 12(this, builder);
        this.xmlClientProvider = new 13(this, builder);
        this.provideMetadataServiceProvider = ScopedProvider.create(PostAdModule_ProvideMetadataServiceFactory.create(builder.postAdModule, this.xmlClientProvider, this.backgroundSchedulerProvider));
        this.provideMetadataBeeProvider = ScopedProvider.create(PostAdModule_ProvideMetadataBeeFactory.create(builder.postAdModule, this.provideMetadataServiceProvider));
        this.trackingPostAdServiceProvider = new 14(this, builder);
        this.provideAdIdProvider = ScopedProvider.create(PostAdModule_ProvideAdIdFactory.create(builder.postAdModule));
        this.providePostAdSummaryPresenterProvider = ScopedProvider.create(PostAdModule_ProvidePostAdSummaryPresenterFactory.create(builder.postAdModule, this.postAdServiceProvider, GatedPostAdSummaryView_Factory.create(), this.postAdRepositoryProvider, this.baseAccountManagerProvider, this.provideImageUploadServiceProvider, this.provideImageDownloadServiceProvider, this.providePostAdValidationRulesProvider, this.localisedTextProvider, this.userServiceProvider, this.providePriceInfoBeeProvider, this.provideContactDetailsDataDraftAdModelConverterProvider, this.networkStateServiceProvider, this.provideMetadataBeeProvider, this.trackingPostAdServiceProvider, this.provideAdIdProvider));
        this.postAdActivityMembersInjector = PostAdActivity_MembersInjector.create(this.baseActivityMembersInjector, this.providePostAdSummaryPresenterProvider, this.provideAdIdProvider);
    }

    public void inject(PostAdActivity postAdActivity) {
        this.postAdActivityMembersInjector.injectMembers(postAdActivity);
    }
}
