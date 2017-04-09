package com.gumtree.android.priceinfo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ebay.classifieds.capi.features.Feature;
import com.ebay.classifieds.capi.features.FeatureGroups;
import com.ebay.classifieds.capi.features.FeaturesApi;
import com.gumtree.Log;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.DraftLocation;
import com.gumtree.android.postad.services.converter.DraftFeatureConverter;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.functions.Func0;

public class ApiPriceInfoService implements PriceInfoService {
    private final FeaturesApi api;
    private final DraftFeatureConverter converter;

    @Inject
    public ApiPriceInfoService(@NonNull FeaturesApi featuresApi, @NonNull DraftFeatureConverter draftFeatureConverter) {
        this.api = (FeaturesApi) Validate.notNull(featuresApi);
        this.converter = (DraftFeatureConverter) Validate.notNull(draftFeatureConverter);
    }

    public Observable<DraftFeature> getFeatureInfo(@Nullable String str, @NonNull DraftCategory draftCategory, @NonNull DraftLocation draftLocation, @Nullable String str2) {
        return getFeatureInfo(ApiPriceInfoService$$Lambda$1.lambdaFactory$(this, str, draftCategory, draftLocation, str2));
    }

    /* synthetic */ Observable lambda$getFeatureInfo$0(@Nullable String str, @NonNull DraftCategory draftCategory, @NonNull DraftLocation draftLocation, @Nullable String str2) {
        return this.api.getFeatures(str, draftCategory.getId(), String.valueOf(draftLocation.getId()), str2);
    }

    @NonNull
    private Observable<DraftFeature> getFeatureInfo(Func0<Observable<FeatureGroups>> func0) {
        return Observable.defer(func0).doOnError(ApiPriceInfoService$$Lambda$2.lambdaFactory$(this)).map(ApiPriceInfoService$$Lambda$3.lambdaFactory$()).concatMap(ApiPriceInfoService$$Lambda$4.lambdaFactory$()).map(ApiPriceInfoService$$Lambda$5.lambdaFactory$(this)).doOnError(ApiPriceInfoService$$Lambda$6.lambdaFactory$(this));
    }

    /* synthetic */ void lambda$getFeatureInfo$1(Throwable th) {
        Log.w(getClass().getSimpleName(), "Unable to interact with API.", th);
    }

    /* synthetic */ DraftFeature lambda$getFeatureInfo$4(Feature feature) {
        return this.converter.apiToModel(feature);
    }

    /* synthetic */ void lambda$getFeatureInfo$5(Throwable th) {
        Log.w(getClass().getSimpleName(), "Unable to parse the API response: ", th);
    }
}
