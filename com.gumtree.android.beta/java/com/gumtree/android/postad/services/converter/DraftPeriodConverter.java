package com.gumtree.android.postad.services.converter;

import android.support.annotation.NonNull;
import com.ebay.classifieds.capi.features.Period;
import com.gumtree.android.message_box.ConversationsIntentService;
import com.gumtree.android.postad.DraftPeriod;

public class DraftPeriodConverter {
    @NonNull
    public DraftPeriod apiToModel(@NonNull Period period) {
        String value = period.getValue();
        Object obj = -1;
        switch (value.hashCode()) {
            case -2020697580:
                if (value.equals(Period.MINUTE)) {
                    obj = null;
                    break;
                }
                break;
            case 67452:
                if (value.equals(Period.DAY)) {
                    obj = 2;
                    break;
                }
                break;
            case 2223588:
                if (value.equals(Period.HOUR)) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case ConversationsIntentService.DEFAULT_PAGE /*0*/:
                return DraftPeriod.MINUTE;
            case HighlightView.GROW_NONE /*1*/:
                return DraftPeriod.HOUR;
            case HighlightView.GROW_LEFT_EDGE /*2*/:
                return DraftPeriod.DAY;
            default:
                return DraftPeriod.UNKNOWN;
        }
    }
}
