package com.gumtree;

public abstract class DevelopmentFlags {
    private static DevelopmentFlags instance;

    public abstract boolean isBingAdsSupportEnabled();

    public abstract boolean isCallistoEnabled();

    public abstract boolean isDeleteInInboxEnabled();

    public abstract boolean isNewLoginEnabled();

    public abstract boolean isNewManageAdsEnabled();

    public abstract boolean isNewPostAdEnabled();

    public abstract boolean isNewSearchKeywordEnabled();

    public abstract boolean isNewSearchResultsEnabled();

    public abstract boolean isPartnershipsEnabled();

    public abstract boolean isSellerOtherItemsEnabled();

    public abstract boolean isSmartLockEnabled();

    public abstract boolean isTreeBayEnabled();

    public static DevelopmentFlags getInstance() {
        return instance;
    }

    public static void setInstance(DevelopmentFlags developmentFlags) {
        instance = developmentFlags;
    }
}
