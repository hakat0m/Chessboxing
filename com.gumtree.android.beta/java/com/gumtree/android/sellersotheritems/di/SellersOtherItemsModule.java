package com.gumtree.android.sellersotheritems.di;

import android.content.Context;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.ads.AdsApi;
import com.gumtree.android.common.paging.PagingConfig;
import com.gumtree.android.common.paging.PagingManager;
import com.gumtree.android.sellersotheritems.DefaultSellersOtherItemsTextProvider;
import com.gumtree.android.sellersotheritems.models.SellerData;
import com.gumtree.android.sellersotheritems.models.converters.SellerAdConverter;
import com.gumtree.android.sellersotheritems.presenters.DefaultSellersOtherItemsPresenter;
import com.gumtree.android.sellersotheritems.presenters.GatedSellersOtherItemsView;
import com.gumtree.android.sellersotheritems.presenters.SellersOtherItemsPresenter;
import com.gumtree.android.sellersotheritems.services.ApiSellerAdsService;
import com.gumtree.android.sellersotheritems.services.BackgroundSellerAdsService;
import com.gumtree.android.sellersotheritems.services.SellerAdsService;
import com.gumtree.android.sellersotheritems.services.SellersOtherItemsTextProvider;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class SellersOtherItemsModule {
    private static final int SELLERS_OTHER_ITEMS_PAGING_SIZE = 20;
    private static final int SELLERS_OTHER_ITEMS_PAGING_THRESHOLD = 5;
    private final SellerData sellerData;

    public SellersOtherItemsModule(SellerData sellerData) {
        this.sellerData = sellerData;
    }

    @Provides
    @SellersOtherItemsScope
    SellersOtherItemsPresenter provideSellersOtherItemsPresenter(GatedSellersOtherItemsView gatedSellersOtherItemsView, SellerAdsService sellerAdsService, SellersOtherItemsTextProvider sellersOtherItemsTextProvider, PagingConfig pagingConfig) {
        return new DefaultSellersOtherItemsPresenter(gatedSellersOtherItemsView, sellerAdsService, sellersOtherItemsTextProvider, new PagingManager(pagingConfig), this.sellerData);
    }

    @Provides
    @SellersOtherItemsScope
    SellerAdsService provideSellerAdsService(AdsApi adsApi, @Named("background") Scheduler scheduler) {
        return new BackgroundSellerAdsService(new ApiSellerAdsService(adsApi, new SellerAdConverter()), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @SellersOtherItemsScope
    SellersOtherItemsTextProvider provideSellersOtherItemsTextProvider(Context context, LocalisedTextProvider localisedTextProvider) {
        return new DefaultSellersOtherItemsTextProvider(context, localisedTextProvider);
    }

    @Provides
    @SellersOtherItemsScope
    public PagingConfig providePagingConfig() {
        return new PagingConfig(SELLERS_OTHER_ITEMS_PAGING_SIZE, SELLERS_OTHER_ITEMS_PAGING_THRESHOLD);
    }
}
