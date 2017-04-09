package com.gumtree.android.postad.di;

import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.phone.PhoneApi;
import com.gumtree.android.vip.presenters.DefaultVipPresenter;
import com.gumtree.android.vip.presenters.GatedVipView;
import com.gumtree.android.vip.presenters.VipPresenter;
import com.gumtree.android.vip.services.ApiVipService;
import com.gumtree.android.vip.services.BackgroundVipService;
import com.gumtree.android.vip.services.VipService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class VipModule {
    private final long adId;

    public VipModule(long j) {
        this.adId = j;
    }

    @Provides
    @VipScope
    public long provideAdId() {
        return this.adId;
    }

    @Provides
    @VipScope
    public PhoneApi providePhoneApi(@Named("xmlClient") ICapiClient iCapiClient) {
        return (PhoneApi) iCapiClient.api(PhoneApi.class);
    }

    @Provides
    @VipScope
    public VipService provideVipService(PhoneApi phoneApi, @Named("background") Scheduler scheduler) {
        return new BackgroundVipService(new ApiVipService(phoneApi), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @VipScope
    public VipPresenter provideVipPresenter(VipService vipService, GatedVipView gatedVipView, long j) {
        return new DefaultVipPresenter(vipService, gatedVipView, j);
    }
}
