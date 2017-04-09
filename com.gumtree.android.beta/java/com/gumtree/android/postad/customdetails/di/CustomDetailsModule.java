package com.gumtree.android.postad.customdetails.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.vrm.VRMApi;
import com.gumtree.android.metadata.service.ApiVRMService;
import com.gumtree.android.metadata.service.BackgroundVRMService;
import com.gumtree.android.metadata.service.MetadataModelConverter;
import com.gumtree.android.metadata.service.VRMService;
import com.gumtree.android.postad.customdetails.CustomDetailsPresenter;
import com.gumtree.android.postad.customdetails.DefaultCustomDetailsPresenter;
import com.gumtree.android.postad.customdetails.GatedCustomDetailsView;
import com.gumtree.android.postad.customdetails.models.CustomDetailsData;
import com.gumtree.android.postad.services.TrackingPostAdService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class CustomDetailsModule {
    private boolean autoValidate;
    private String categoryId;
    private CustomDetailsData customDetailsData;

    public CustomDetailsModule(CustomDetailsData customDetailsData, boolean z, String str) {
        this.customDetailsData = customDetailsData;
        this.autoValidate = z;
        this.categoryId = str;
    }

    @Provides
    @CustomDetailsScope
    public VRMService provideVRMService(@Named("xmlClient") ICapiClient iCapiClient, @Named("background") Scheduler scheduler) {
        return new BackgroundVRMService(new ApiVRMService((VRMApi) iCapiClient.api(VRMApi.class), new MetadataModelConverter()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @CustomDetailsScope
    CustomDetailsPresenter provideCustomDetailsPresenter(GatedCustomDetailsView gatedCustomDetailsView, VRMService vRMService, TrackingPostAdService trackingPostAdService) {
        return new DefaultCustomDetailsPresenter(gatedCustomDetailsView, this.customDetailsData, this.autoValidate, this.categoryId, vRMService, trackingPostAdService);
    }
}
