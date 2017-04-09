package com.gumtree.android.postad.services;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.ads.ActiveFeature;
import com.gumtree.Log;
import com.gumtree.android.postad.DraftFeature.Type;
import com.gumtree.android.postad.services.converter.DraftFeatureTypeConverter;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration$Builder;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class ActiveFeatureConverter {
    private final DraftFeatureTypeConverter draftFeatureTypeConverter;

    @Inject
    public ActiveFeatureConverter(DraftFeatureTypeConverter draftFeatureTypeConverter) {
        this.draftFeatureTypeConverter = draftFeatureTypeConverter;
    }

    @NonNull
    public List<Type> apiToModel(@NonNull List<ActiveFeature> list) {
        List<Type> arrayList = new ArrayList();
        for (ActiveFeature apiToModel : list) {
            arrayList.add(apiToModel(apiToModel));
        }
        return arrayList;
    }

    @NonNull
    public Type apiToModel(@NonNull ActiveFeature activeFeature) {
        Type apiToModel = this.draftFeatureTypeConverter.apiToModel(activeFeature.getType());
        switch (1.$SwitchMap$com$gumtree$android$postad$DraftFeature$Type[apiToModel.ordinal()]) {
            case HighlightView.GROW_NONE /*1*/:
            case HighlightView.GROW_LEFT_EDGE /*2*/:
            case ImageLoaderConfiguration$Builder.DEFAULT_THREAD_POOL_SIZE /*3*/:
            case HighlightView.GROW_RIGHT_EDGE /*4*/:
                return apiToModel;
            default:
                Log.d(getClass().getSimpleName(), "Unsupported feature type [" + apiToModel + "]");
                return Type.UNSUPPORTED;
        }
    }
}
