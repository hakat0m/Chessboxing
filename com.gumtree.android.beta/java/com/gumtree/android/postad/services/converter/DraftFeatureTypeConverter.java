package com.gumtree.android.postad.services.converter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.ebay.classifieds.capi.features.Feature;
import com.gumtree.Log;
import com.gumtree.android.logging.Timber;
import com.gumtree.android.postad.DraftFeature.Type;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;

public class DraftFeatureTypeConverter {
    private static final String CLOSE_BRACKET = "]";
    private static final String UNSUPPORTED_FEATURE_TYPE = "Unsupported feature type [";

    @NonNull
    public Type apiToModel(Feature.Type type) {
        Type type2 = Type.UNSUPPORTED;
        if (type == null) {
            return type2;
        }
        switch (1.$SwitchMap$com$ebay$classifieds$capi$features$Feature$Type[type.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return Type.BUMPED_UP;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return Type.IN_SPOTLIGHT;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return Type.FEATURED;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return Type.URGENT;
            case Timber.WARN /*5*/:
                return Type.INSERTION;
            default:
                Log.d(getClass().getSimpleName(), UNSUPPORTED_FEATURE_TYPE + type + CLOSE_BRACKET);
                return type2;
        }
    }

    @Nullable
    public Feature.Type modelToApi(Type type) {
        if (type == null) {
            return null;
        }
        switch (1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[type.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
                return Feature.Type.BUMP_UP;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return Feature.Type.HIGHLIGHT;
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
                return Feature.Type.TOP_AD;
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return Feature.Type.URGENT;
            case Timber.WARN /*5*/:
                return Feature.Type.INSERTION;
            default:
                Log.d(getClass().getSimpleName(), UNSUPPORTED_FEATURE_TYPE + type + CLOSE_BRACKET);
                return null;
        }
    }
}
