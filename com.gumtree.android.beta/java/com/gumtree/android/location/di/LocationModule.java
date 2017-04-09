package com.gumtree.android.location.di;

import android.content.Context;
import com.ebay.classifieds.capi.ICapiClient;
import com.ebay.classifieds.capi.LocalisedTextProvider;
import com.ebay.classifieds.capi.locations.LocationsApi;
import com.ebay.classifieds.capi.suggestions.SuggestionsApi;
import com.gumtree.android.location.model.LocationData;
import com.gumtree.android.location.presenters.DefaultLocationPresenter;
import com.gumtree.android.location.presenters.GatedLocationView;
import com.gumtree.android.location.presenters.LocationPresenter;
import com.gumtree.android.location.service.GeocoderLocationService;
import com.gumtree.android.location.services.ApiLocationService;
import com.gumtree.android.location.services.BackgroundLocationServiceDispatcher;
import com.gumtree.android.location.services.CachedLocationService;
import com.gumtree.android.location.services.DefaultLocationModelConverter;
import com.gumtree.android.location.services.LocationModelConverter;
import com.gumtree.android.location.services.LocationService;
import com.gumtree.android.location.services.LocationServiceDispatcher;
import com.gumtree.android.location.services.TrackingLocationService;
import dagger.Module;
import dagger.Provides;
import javax.inject.Named;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

@Module
public class LocationModule {
    private static final int LOCATION_CACHE_CAPACITY = 8;
    private final LocationData location;

    public LocationModule(LocationData locationData) {
        this.location = locationData;
    }

    @Provides
    public LocationModelConverter providesLocationModelConverter() {
        return new DefaultLocationModelConverter();
    }

    @Provides
    public LocationService providesLocationService(@Named("xmlClient") ICapiClient iCapiClient, LocationModelConverter locationModelConverter, Context context, @Named("background") Scheduler scheduler) {
        return new BackgroundLocationServiceDispatcher(new LocationServiceDispatcher(new CachedLocationService(new ApiLocationService((SuggestionsApi) iCapiClient.api(SuggestionsApi.class), locationModelConverter), LOCATION_CACHE_CAPACITY), new GeocoderLocationService(context, scheduler), (LocationsApi) iCapiClient.api(LocationsApi.class)), AndroidSchedulers.mainThread(), scheduler);
    }

    @Provides
    @LocationScope
    public LocationPresenter providePostAdContactDetailsPresenter(GatedLocationView gatedLocationView, LocationService locationService, LocalisedTextProvider localisedTextProvider, TrackingLocationService trackingLocationService) {
        return new DefaultLocationPresenter(gatedLocationView, locationService, this.location, localisedTextProvider, trackingLocationService);
    }
}
