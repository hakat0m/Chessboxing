package com.gumtree.android.postad.di;

import android.content.Context;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.gumtree.android.postad.promote.DefaultPromotionPresenter;
import com.gumtree.android.postad.promote.PromotionPresenter;
import com.gumtree.android.postad.promote.ResourceStringProvider;
import com.gumtree.android.postad.promote.StringProvider;
import dagger.Module;
import dagger.Provides;

@Module
public class PromotionModule {
    @Provides
    @PromotionScope
    public PromotionPresenter providesPromotionPresenter(DefaultPromotionPresenter defaultPromotionPresenter) {
        return defaultPromotionPresenter;
    }

    @Provides
    @PromotionScope
    public StringProvider providesStringProvider(LocalisedTextProvider localisedTextProvider, Context context) {
        return new ResourceStringProvider(localisedTextProvider, context.getResources());
    }
}
