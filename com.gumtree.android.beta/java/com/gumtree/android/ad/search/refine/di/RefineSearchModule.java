package com.gumtree.android.ad.search.refine.di;

import com.gumtree.andorid.ad.search.services.refine.TrackingRefinePanelService;
import com.gumtree.android.ad.search.presenters.refine.DefaultRefineSearchPresenter;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchGatedView;
import com.gumtree.android.ad.search.presenters.refine.RefineSearchPresenter;
import com.gumtree.android.services.StaticTrackingService;
import dagger.Module;
import dagger.Provides;

@Module
public class RefineSearchModule {
    @Provides
    @RefineSearchScope
    TrackingRefinePanelService provideTrackingRefinePanelService(StaticTrackingService staticTrackingService) {
        return staticTrackingService;
    }

    @Provides
    @RefineSearchScope
    RefineSearchPresenter provideRefineSearchPresenter(RefineSearchGatedView refineSearchGatedView, TrackingRefinePanelService trackingRefinePanelService) {
        return new DefaultRefineSearchPresenter(refineSearchGatedView, trackingRefinePanelService);
    }
}
