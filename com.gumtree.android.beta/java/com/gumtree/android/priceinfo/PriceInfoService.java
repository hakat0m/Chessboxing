package com.gumtree.android.priceinfo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.DraftLocation;
import rx.Observable;

public interface PriceInfoService {
    Observable<DraftFeature> getFeatureInfo(@Nullable String str, @NonNull DraftCategory draftCategory, @NonNull DraftLocation draftLocation, @Nullable String str2);
}
