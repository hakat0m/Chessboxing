package com.gumtree.android.manageads.services;

public interface TrackingManageAdsService {
    void eventAdStatusInfoPressed(String str);

    void eventDeleteAdBegin();

    void eventDeleteAdCancel();

    void eventDeleteAdFail(String str);

    void eventDeleteAdSuccess();

    void eventEditAdBegin();

    void eventManageAdsBegin();

    void eventNeedsEditingCSLink();

    void eventPostAdBeginFromEmptyPage();

    void eventPostAdBeginFromHeader();

    void eventPreviewAdBegin();

    void eventRemovedCSLink();

    void eventShowActiveFragment();

    void eventShowInactiveFragment();
}
