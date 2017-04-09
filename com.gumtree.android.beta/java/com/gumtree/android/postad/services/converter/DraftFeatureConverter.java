package com.gumtree.android.postad.services.converter;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.features.Feature;
import com.gumtree.android.postad.DraftFeature;
import javax.inject.Inject;
import org.apache.commons.lang3.Validate;

public class DraftFeatureConverter {
    private final DraftFeatureTypeConverter draftFeatureTypeConverter;
    private final DraftOptionConverter optionConverter;

    @Inject
    public DraftFeatureConverter(@NonNull DraftFeatureTypeConverter draftFeatureTypeConverter, @NonNull DraftOptionConverter draftOptionConverter) {
        this.optionConverter = (DraftOptionConverter) Validate.notNull(draftOptionConverter);
        this.draftFeatureTypeConverter = (DraftFeatureTypeConverter) Validate.notNull(draftFeatureTypeConverter);
    }

    public DraftFeature apiToModel(Feature feature) {
        return DraftFeature.builder().description(feature.getDescription()).type(this.draftFeatureTypeConverter.apiToModel(feature.getType())).identifier(feature.getName()).options(this.optionConverter.apiToModel(feature.getOptions())).build();
    }
}
