package com.gumtree.android.priceinfo;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.gumtree.android.category.model.DraftCategory;
import com.gumtree.android.postad.DraftFeature;
import com.gumtree.android.postad.DraftLocation;
import org.apache.commons.lang3.Validate;
import rx.Observable;
import rx.Scheduler;

public class BackgroundPriceInfoService implements PriceInfoService {
    private final PriceInfoService decorated;
    private final Scheduler notifications;
    private final Scheduler worker;

    public BackgroundPriceInfoService(@NonNull PriceInfoService priceInfoService, @NonNull Scheduler scheduler, @NonNull Scheduler scheduler2) {
        this.decorated = (PriceInfoService) Validate.notNull(priceInfoService);
        this.notifications = (Scheduler) Validate.notNull(scheduler);
        this.worker = (Scheduler) Validate.notNull(scheduler2);
    }

    public Observable<DraftFeature> getFeatureInfo(@Nullable String str, @NonNull DraftCategory draftCategory, @NonNull DraftLocation draftLocation, @Nullable String str2) {
        return this.decorated.getFeatureInfo(str, draftCategory, draftLocation, str2).subscribeOn(this.worker).observeOn(this.notifications);
    }
}
