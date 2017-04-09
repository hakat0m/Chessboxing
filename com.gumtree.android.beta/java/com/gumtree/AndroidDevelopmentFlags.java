package com.gumtree;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.BoolRes;
import android.support.annotation.NonNull;

public class AndroidDevelopmentFlags extends DevelopmentFlags {
    private final Resources resources;

    public AndroidDevelopmentFlags(@NonNull Context context) {
        this.resources = context.getResources();
    }

    public boolean isDeleteInInboxEnabled() {
        return getBooleanDefaultFalse(2131427351);
    }

    public boolean isNewPostAdEnabled() {
        return getBooleanDefaultFalse(2131427354);
    }

    public boolean isPartnershipsEnabled() {
        return getBooleanDefaultFalse(2131427355);
    }

    public boolean isTreeBayEnabled() {
        return getBooleanDefaultFalse(2131427362);
    }

    public boolean isBingAdsSupportEnabled() {
        return getBooleanDefaultFalse(2131427350);
    }

    public boolean isNewLoginEnabled() {
        return getBooleanDefaultFalse(2131427352);
    }

    public boolean isSmartLockEnabled() {
        return getBooleanDefaultFalse(2131427360);
    }

    public boolean isCallistoEnabled() {
        return getBooleanDefaultFalse(2131427361);
    }

    public boolean isSellerOtherItemsEnabled() {
        return getBooleanDefaultFalse(2131427359);
    }

    public boolean isNewSearchResultsEnabled() {
        return getBooleanDefaultFalse(2131427358);
    }

    public boolean isNewSearchKeywordEnabled() {
        return getBooleanDefaultFalse(2131427357);
    }

    public boolean isNewManageAdsEnabled() {
        return getBooleanDefaultFalse(2131427353);
    }

    private boolean getBooleanDefaultFalse(@BoolRes int i) {
        try {
            return this.resources.getBoolean(i);
        } catch (Throwable e) {
            Log.d(getClass().getSimpleName(), "Unexpected problem: ", e);
            return false;
        }
    }
}
