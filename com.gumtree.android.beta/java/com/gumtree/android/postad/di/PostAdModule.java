package com.gumtree.android.postad.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.ebay.classifieds.capi.metadata.MetadataApi;
import com.ebay.classifieds.capi.pictures.PicturesApi;
import com.gumtree.android.auth.BaseAccountManager;
import com.gumtree.android.metadata.service.ApiMetadataService;
import com.gumtree.android.metadata.service.BackgroundMetadataService;
import com.gumtree.android.metadata.service.MetadataModelConverter;
import com.gumtree.android.metadata.service.MetadataService;
import com.gumtree.android.postad.DraftAd;
import com.gumtree.android.postad.presenters.DefaultPostAdSummaryPresenter;
import com.gumtree.android.postad.presenters.GatedPostAdSummaryView;
import com.gumtree.android.postad.presenters.MetadataBee;
import com.gumtree.android.postad.presenters.PostAdSummaryPresenter;
import com.gumtree.android.postad.presenters.PriceInfoBee;
import com.gumtree.android.postad.services.BackgroundImageDownloadService;
import com.gumtree.android.postad.services.BackgroundImageUploadService;
import com.gumtree.android.postad.services.Cache;
import com.gumtree.android.postad.services.CachingImageUploadService;
import com.gumtree.android.postad.services.CapiImageUploadService;
import com.gumtree.android.postad.services.ContactDetailsDataDraftAdModelConverter;
import com.gumtree.android.postad.services.ImageDownloadService;
import com.gumtree.android.postad.services.ImageUploadService;
import com.gumtree.android.postad.services.OkHttpImageDownloadService;
import com.gumtree.android.postad.services.PostAdService;
import com.gumtree.android.postad.services.TrackingPostAdService;
import com.gumtree.android.postad.validation.EmittingValidationRules;
import com.gumtree.android.postad.validation.PostAdValidation;
import com.gumtree.android.postad.validation.PostAdValidationMessages;
import com.gumtree.android.postad.validation.PostAdValidationRules;
import com.gumtree.android.priceinfo.PriceInfoService;
import com.gumtree.android.repositories.Repository;
import com.gumtree.android.services.NetworkStateService;
import com.gumtree.android.userprofile.services.UserService;
import com.squareup.okhttp.OkHttpClient;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class PostAdModule {
    private final String adId;

    public PostAdModule(String str) {
        this.adId = str;
    }

    @Provides
    @PostAdScope
    public String provideAdId() {
        return this.adId;
    }

    @Provides
    @PostAdScope
    public AdsApi provideAdsApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (AdsApi) iCapiClient.api(AdsApi.class);
    }

    @Provides
    @PostAdScope
    public ImageDownloadService provideImageDownloadService(Context context, @Named("background") Scheduler scheduler) {
        return new BackgroundImageDownloadService(new OkHttpImageDownloadService(context.getFilesDir(), new OkHttpClient()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @PostAdScope
    public ImageUploadService provideImageUploadService(CapiImageUploadService capiImageUploadService, @Named("background") Scheduler scheduler) {
        return new BackgroundImageUploadService(new CachingImageUploadService(capiImageUploadService, new Cache()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @PostAdScope
    public PicturesApi providePicturesApi(@Named("picturesUploadClient") ICapiClient iCapiClient) {
        return (PicturesApi) iCapiClient.api(PicturesApi.class);
    }

    @Provides
    @PostAdScope
    public MetadataService provideMetadataService(@Named("xmlClient") ICapiClient iCapiClient, @Named("background") Scheduler scheduler) {
        return new BackgroundMetadataService(new ApiMetadataService((MetadataApi) iCapiClient.api(MetadataApi.class), new MetadataModelConverter()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @PostAdScope
    public PostAdValidation providePostAdValidation(PostAdValidationMessages postAdValidationMessages) {
        return postAdValidationMessages;
    }

    @Provides
    @PostAdScope
    public PostAdValidationRules providePostAdValidationRules(PostAdValidationMessages postAdValidationMessages) {
        return new PostAdValidationRules(postAdValidationMessages);
    }

    @Provides
    @PostAdScope
    public ContactDetailsDataDraftAdModelConverter provideContactDetailsDataDraftAdModelConverter() {
        return new ContactDetailsDataDraftAdModelConverter();
    }

    @Provides
    @PostAdScope
    public MetadataBee provideMetadataBee(MetadataService metadataService) {
        return new MetadataBee(metadataService);
    }

    @Provides
    @PostAdScope
    public PriceInfoBee providePriceInfoBee(PriceInfoService priceInfoService) {
        return new PriceInfoBee(priceInfoService);
    }

    @Provides
    @PostAdScope
    public PostAdSummaryPresenter providePostAdSummaryPresenter(PostAdService postAdService, GatedPostAdSummaryView gatedPostAdSummaryView, Repository<DraftAd> repository, BaseAccountManager baseAccountManager, ImageUploadService imageUploadService, ImageDownloadService imageDownloadService, PostAdValidationRules postAdValidationRules, LocalisedTextProvider localisedTextProvider, UserService userService, PriceInfoBee priceInfoBee, ContactDetailsDataDraftAdModelConverter contactDetailsDataDraftAdModelConverter, NetworkStateService networkStateService, MetadataBee metadataBee, TrackingPostAdService trackingPostAdService, String str) {
        return new DefaultPostAdSummaryPresenter(gatedPostAdSummaryView, postAdService, repository, baseAccountManager, imageUploadService, imageDownloadService, userService, new EmittingValidationRules(postAdValidationRules), localisedTextProvider, priceInfoBee, contactDetailsDataDraftAdModelConverter, networkStateService, metadataBee, trackingPostAdService, str);
    }
}
